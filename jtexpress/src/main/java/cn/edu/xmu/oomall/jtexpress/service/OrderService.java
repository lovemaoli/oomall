package cn.edu.xmu.oomall.jtexpress.service;

import cn.edu.xmu.oomall.jtexpress.dao.CustomerDao;
import cn.edu.xmu.oomall.jtexpress.dao.OrderDao;
import cn.edu.xmu.oomall.jtexpress.dao.TraceDetailDao;
import cn.edu.xmu.oomall.jtexpress.dao.bo.Order;
import cn.edu.xmu.oomall.jtexpress.exception.JTException;
import cn.edu.xmu.oomall.jtexpress.exception.ReturnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
/**
 * @author 徐森彬
 * 2023-dgn3-02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TraceDetailDao traceDetailDao;

    public Order addOrder(Order order) {
        logger.debug("顾客{}创建订单:{}", order.getCustomerCode(), order);
        //查找下运单的用户是否存在
        if (!customerDao.checkByCode(order.getCustomerCode())) throw new JTException(ReturnError.ILLEGAL_PARAMETER);
        order = orderDao.addOrder(order);

        //为了简便，创建一个异步任务，新增物流轨迹，以满足查询轨迹的需求
        createTrace(order);

        return order;

    }

    public Order cancelOrder(Order order) {
        logger.debug("顾客{}取消订单:{}", order.getCustomerCode(), order);
        //查找下运单的用户是否存在
        if (!customerDao.checkByCode(order.getCustomerCode())) throw new JTException(ReturnError.ILLEGAL_PARAMETER);

        return orderDao.cancelOrder(order);

    }

    public void createTrace(Order order) {
        //不是订单完成状态不创建轨迹
        if (order.getStatus() != Order.StatusEnum.COMPLETED.getCode()) {
            return;
        }
        try {
            traceDetailDao.createRandomTrace(order.getBillCode());
        } catch (RuntimeException e) {
            logger.debug("运单{}创建轨迹出错：{}", order.getBillCode(), e.getMessage());
        }
    }
}
