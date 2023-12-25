package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.mapper.OrderItemPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RefreshScope
public class OrderItemDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final OrderItemPoMapper orderItemPoMapper;

    public OrderItemDao(OrderItemPoMapper orderItemPoMapper) {
        this.orderItemPoMapper = orderItemPoMapper;
    }

    public OrderItem build(OrderItemPo po){
        return CloneFactory.copy(new OrderItem(), po);
    }

    public OrderItem findById(Long orderid, Long orderitemid) {
        Optional<OrderItemPo> orderItemPo = orderItemPoMapper.findById(orderid, orderitemid);
        if(orderItemPo.isPresent()) {
            return build(orderItemPo.get());
        } else {
            return null;
        }
    }
}
