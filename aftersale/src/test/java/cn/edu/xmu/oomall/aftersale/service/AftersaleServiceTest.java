package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.*;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ExchangeAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.MaintainAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ReturnAftersale;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersaleExpressPo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DokinLee
 */

@SpringBootTest(classes = AftersaleApplication.class)
@Transactional
public class AftersaleServiceTest {

    @Autowired
    private AftersaleService aftersaleService;

    @Autowired
    private AftersaleExpressDao aftersaleExpressDao;

    @Autowired
    private AftersaleDao aftersaleDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private ExpressDao expressDao;

    @Autowired
    private PaymentDao paymentDao;

    @Test
    public void buildAftersaleTest() {
        Aftersale aftersale = new Aftersale();

        aftersale.setType(Aftersale.RETURN);
        aftersale = aftersaleService.buildAftersale(aftersale);
        aftersale.setShop_id(1L);
        aftersale.setExpressDao(expressDao);
        aftersale.setAftersaleExpressDao(aftersaleExpressDao);
        aftersale.setOrderItemDao(orderItemDao);
        aftersale.setPaymentDao(paymentDao);
        aftersale.setAftersaleDao(aftersaleDao);
        aftersale.shopAudit(1L);
        aftersale.examine(1L, true);
        aftersale.examine(1L, false);
        aftersale.examine(2L, true);

        aftersale.setType(Aftersale.EXCHANGE);
        aftersale = aftersaleService.buildAftersale(aftersale);
        aftersale.setShop_id(1L);
        aftersale.setExpressDao(expressDao);
        aftersale.setAftersaleExpressDao(aftersaleExpressDao);
        aftersale.setOrderItemDao(orderItemDao);
        aftersale.setPaymentDao(paymentDao);
        aftersale.setAftersaleDao(aftersaleDao);
        aftersale.shopAudit(1L);
        aftersale.examine(1L, true);
        aftersale.examine(1L, false);
        aftersale.examine(2L, true);

        aftersale.setType(Aftersale.REPAIR);
        aftersaleService.buildAftersale(aftersale);
    }

    @Test
    public void shopReceive() {
        aftersaleService.shopReceive(100L,100L,true,"",new UserDto());
        aftersaleService.shopReceive(4L,1L,true,"",new UserDto());
//        aftersaleService.shopReceive(2L,1L,true,"",new UserDto());
    }

    @Test
    public void auditAftersaleTest() {
        aftersaleService.auditAftersale(1000L, 100L, false, "", new UserDto());

        //aftersaleService.auditAftersale(1L, 1L, true, "", new UserDto());
    }


    @Test
    public void applyAftersaleTest() {
        aftersaleService.applyAftersale(1000L, 1000L, new Aftersale(), new UserDto());
        Aftersale aftersale = new Aftersale();
        aftersale.setId(20L);
        aftersale.setType(Aftersale.REPAIR);
        aftersale.setAftersaleDao(this.aftersaleDao);
//        aftersaleService.applyAftersale(1L, 1L, aftersale, new UserDto());
    }

}