package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.OrderItemDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ExchangeAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.MaintainAftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ReturnAftersale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AftersaleService {

    private final Logger logger = LoggerFactory.getLogger(AftersaleService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final AftersaleDao aftersaleDao;

    private final OrderItemDao orderItemDao;

    @Autowired
    public AftersaleService(AftersaleDao aftersaleDao, OrderItemDao orderItemDao) {
        this.aftersaleDao = aftersaleDao;
        this.orderItemDao = orderItemDao;
    }

    public Aftersale findById(Long id) {
        return this.aftersaleDao.findById(id);
    }


    public ReturnObject applyAftersale(Long orderid, Long orderitemid, Aftersale bo, UserDto user) {
        OrderItem orderItem = orderItemDao.findById(orderid, orderitemid);
        if(orderItem == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        Aftersale aftersale = orderItem.createAftersale(bo, user);
        return new ReturnObject(aftersale);
    }

    public ReturnNo shopReceive(Long billcode, Long shopid, Boolean confirm, String conclusion, UserDto user) {
        Aftersale aftersale = this.aftersaleDao.findByBillCode(billcode, shopid);
        if(aftersale == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        aftersale = buildAftersale(aftersale);
        if(aftersale.getType() == Aftersale.REPAIR){
            return ReturnNo.AFTERSALE_STATENOTALLOW;
        }
        aftersale.setConclusion(conclusion);
        ReturnNo ret = aftersale.examine(shopid, confirm);
        return ret;
    }

    public Aftersale buildAftersale(Aftersale aftersale){
        if(aftersale.getType()==Aftersale.RETURN)
            aftersale = new ReturnAftersale(aftersale);
        else if(aftersale.getType()==Aftersale.EXCHANGE)
            aftersale = new ExchangeAftersale(aftersale);
        else if(aftersale.getType()==Aftersale.REPAIR)
            aftersale = new MaintainAftersale(aftersale);
        return aftersale;
    }

    public ReturnNo auditAftersale(Long aid, Long shopid, Boolean confirm, String conclusion, UserDto user) {
        Aftersale aftersale = this.aftersaleDao.findById(aid);
        if(aftersale == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        aftersale = buildAftersale(aftersale);
        ReturnNo ret = aftersale.audit(shopid, confirm, conclusion, user);
        return ret;
    }
}
