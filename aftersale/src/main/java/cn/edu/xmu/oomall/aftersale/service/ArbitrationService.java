package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.controller.dto.ArbitrationDto;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.arbitration.Arbitration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ArbitrationService {

    private final Logger logger = LoggerFactory.getLogger(ArbitrationService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final AftersaleDao aftersaleDao;
    private final ArbitrationDao arbitrationDao;

    @Autowired
    public ArbitrationService(AftersaleDao aftersaleDao, ArbitrationDao arbitrationDao) {
        this.aftersaleDao = aftersaleDao;
        this.arbitrationDao = arbitrationDao;
    }

    public Arbitration findById(Long id) {
        return this.arbitrationDao.findById(id);
    }

    public ArbitrationDto applyAftersaleArbitration(Long aid, String reason, UserDto user) {
        Aftersale aftersale = this.aftersaleDao.findById(aid);
        if (aftersale == null) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        ArbitrationDto arbitrationDto = aftersale.createArbitration(reason, user);
        return arbitrationDto;

    }

    public ReturnNo cancelArbitration(Long aid, UserDto user) {
        Arbitration arb = this.arbitrationDao.findById(aid);
        if (arb == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        if (arb.getCustomer_id() != user.getId()) {
            return ReturnNo.ARBITRATION_NOT_APPLICANT;
        }
        if (arb.getStatus() == Arbitration.SUCCESS || arb.getStatus() == Arbitration.CANCEL) {
            return ReturnNo.ARBITRATION_STATE_NOTALLOW;
        }
        changeStatus(arb, Arbitration.CANCEL);
        return ReturnNo.OK;
    }

    public void changeStatus(Arbitration arb, Integer status) {
        arb.setStatus(status);
        this.arbitrationDao.update(arb);
    }


}
