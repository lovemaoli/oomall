package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public class PaymentDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;


    public ReturnNo createRefund(OrderItem orderItem, Long shopid) {
        return ReturnNo.OK; //TODO 直接成功
    }
}
