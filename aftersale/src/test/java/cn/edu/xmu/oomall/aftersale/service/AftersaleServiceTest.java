package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.ExpressDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ExchangeAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.MaintainAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ReturnAftersale;
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

    @Test
    public void buildAftersaleTest() {
        Aftersale aftersale = new Aftersale();

        aftersale.setType(Aftersale.RETURN);
        aftersaleService.buildAftersale(aftersale);

        aftersale.setType(Aftersale.EXCHANGE);
        aftersaleService.buildAftersale(aftersale);

        aftersale.setType(Aftersale.REPAIR);
        aftersaleService.buildAftersale(aftersale);
    }

}