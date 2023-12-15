package cn.edu.xmu.oomall.jtexpress.dao;

import cn.edu.xmu.oomall.jtexpress.dao.bo.PersonInfo;
import cn.edu.xmu.oomall.jtexpress.util.CloneFactory;
import cn.edu.xmu.oomall.jtexpress.dao.bo.Order;
import cn.edu.xmu.oomall.jtexpress.exception.JTException;
import cn.edu.xmu.oomall.jtexpress.exception.ReturnError;
import cn.edu.xmu.oomall.jtexpress.mapper.OrderPoMapper;
import cn.edu.xmu.oomall.jtexpress.mapper.PersonInfoPoMapper;
import cn.edu.xmu.oomall.jtexpress.mapper.po.OrderPo;
import cn.edu.xmu.oomall.jtexpress.mapper.po.PersonInfoPo;
import cn.edu.xmu.oomall.jtexpress.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Repository
@RefreshScope
public class OrderDao {
    private final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    @Autowired
    private OrderPoMapper orderPoMapper;


    @Autowired
    private PersonInfoPoMapper personInfoPoMapper;

    public Order addOrder(Order order) {

        builder(order);

        //设置订单状态为未完成
        order.setStatus(Order.StatusEnum.UNCOMPLETED.getCode());

        //为了方便，如果商户申请的上门揽件时间早于当前时间，执行揽件，更改订单状态为已完成
        if (order.getSendStartTime() != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(order.getSendStartTime()) || currentTime.equals(order.getSendStartTime())) {
                order.setStatus(Order.StatusEnum.COMPLETED.getCode());
            }
        }

        //判断订单是否已经存在
        Order oldOrder = findOrderByTxLogisticId(order.getTxLogisticId());

        //判断订单状态是否可以修改
        if (null != oldOrder) {
            //判断订单号是否非法,运单号与客户订单号不同违法
            if (null != order.getBillCode() && !Objects.equals(order.getBillCode(), oldOrder.getBillCode()))
                throw new JTException(ReturnError.INVALID_WAYBILL_NO);

            //状态无法修改会抛出JTException异常
            oldOrder.checkStatus(oldOrder.getStatus(), order.getStatus());

            //赋值id
            order.setId(oldOrder.getId());
            order.setBillCode(oldOrder.getBillCode());

            //赋值senderId
            order.getSender().setId(oldOrder.getSender().getId());
            //如果sender没有修改，设置alter为false
            if (oldOrder.getSender().equals(order.getSender())) {
                order.getSender().setAlter(false);
            }

            //赋值receiverId
            order.getReceiver().setId(oldOrder.getReceiver().getId());
            //如果receiver没有修改，设置alter为false
            if (oldOrder.getReceiver().equals(order.getReceiver())) {
                order.getReceiver().setAlter(false);
            }

            //更新order
            order = order.update();
        } else {
            //保存order
            order = order.save();
        }
        return order;
    }

    public Order cancelOrder(Order order) {
        //判断订单是否已经存在
        Order oldOrder = findOrderByTxLogisticId(order.getTxLogisticId());
        //如果不存在，抛出非法参数异常
        if (null == oldOrder) throw new JTException(ReturnError.ILLEGAL_PARAMETER);

        //如果该订单不属于该用户，抛出非法参数异常
        if (order.getCustomerCode() == null || !(order.getCustomerCode().equals(oldOrder.getCustomerCode()))) {
            throw new JTException(ReturnError.ILLEGAL_PARAMETER);
        }

        //判断是否可以取消，不可以取消抛出异常
        order.checkStatus(oldOrder.getStatus(), Order.StatusEnum.CANCELED.getCode());

        oldOrder.setStatus(Order.StatusEnum.CANCELED.getCode());

        return oldOrder.save();

    }


    public Order saveOrder(Order order) throws RuntimeException {

        //保存寄件人收件人信息
        savePersonInfo(order);

        //todo:保存寄件物品信息

        //如果运单号为空，生成运单号
        if (null == order.getBillCode()) order.setBillCode(generateWaybillNumber());

        //保存订单
        OrderPo orderPo = CloneFactory.copy(new OrderPo(), order);
        logger.debug("save orderPo : {}", orderPo);
        orderPo = orderPoMapper.save(orderPo);
        CloneFactory.copy(order, orderPo);

        return order;
    }

    public Order updateOrder(Order order) throws RuntimeException {
        //保存寄件人收件人信息
        savePersonInfo(order);

        //保存订单
        OrderPo orderPo = CloneFactory.copy(new OrderPo(), order);
        logger.debug("update orderPo : {}", orderPo);
        orderPo = orderPoMapper.save(orderPo);
        CloneFactory.copy(order, orderPo);
        return order;
    }

    public void savePersonInfo(Order order) throws RuntimeException {
        if (order.getSender().getAlter()) {
            //寄件人信息
            PersonInfoPo senderPo = CloneFactory.copy(new PersonInfoPo(), order.getSender());
            //保存寄件人信息
            logger.debug("save senderPo : {}", senderPo);
            order.getSender().setId(personInfoPoMapper.save(senderPo).getId());
        }
        if (order.getReceiver().getAlter()) {
            //收件人信息
            PersonInfoPo receiverPo = CloneFactory.copy(new PersonInfoPo(), order.getReceiver());
            //保存收件人信息
            logger.debug("save receivePo : {}", receiverPo);
            order.getReceiver().setId(personInfoPoMapper.save(receiverPo).getId());
        }
    }

    public Order findOrderByTxLogisticId(String txLogisticId) {
        Optional<OrderPo> orderPo = orderPoMapper.findByTxLogisticId(txLogisticId);
        if (orderPo.isEmpty()) return null;
        Order order = CloneFactory.copy(new Order(), orderPo.get());

        //设置sender
        Optional<PersonInfoPo> senderPo = personInfoPoMapper.findById(order.getSender().getId());
        if (senderPo.isEmpty()) throw new JTException(ReturnError.SYSTEM_ERROR);
        CloneFactory.copy(order.getSender(), senderPo.get());

        //设置receiver
        Optional<PersonInfoPo> receiverPo = personInfoPoMapper.findById(order.getReceiver().getId());
        if (receiverPo.isEmpty()) throw new JTException(ReturnError.SYSTEM_ERROR);
        CloneFactory.copy(order.getReceiver(), receiverPo.get());

        this.builder(order);
        return order;
    }

    public void builder(Order order) {
        order.setOrderDao(this);
    }

    private String generateWaybillNumber() {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();

        // 生成随机偏移量
        long randomShift = new Random().nextInt(10) + 5;

        // 在时间戳上随机偏移
        long shiftedTimestamp = (timestamp << randomShift) | (timestamp >>> (64 - randomShift));

        // 使用时间戳和随机数生成运单号
        return String.format("JT%d%06d", shiftedTimestamp, new Random().nextInt(100000));
    }


}
