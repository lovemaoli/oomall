package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.controller.vo.DraftServiceVo;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.dao.RegionDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.po.DraftServicePo;
import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DraftServiceService {
    private final Logger logger = LoggerFactory.getLogger(DraftServiceService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final DraftServiceDao draftServiceDao;

    private final ServiceProviderDao serviceProviderDao;

    private final RegionDao regionDao;

    @Autowired
    public DraftServiceService(DraftServiceDao draftServiceDao, ServiceProviderDao serviceProviderDao, RegionDao regionDao) {
        this.draftServiceDao = draftServiceDao;
        this.serviceProviderDao = serviceProviderDao;
        this.regionDao = regionDao;
    }

    /**
     * 服务商定义在某个地区为某种商品的服务
     * @param mid
     * @param rid
     * @param vo
     * @param user
     * @return
     */
    public ReturnObject defServiceForProductInRegion(Long mid, Long rid, DraftServiceVo vo, UserDto user) {
        ServiceProvider serviceProvider = this.serviceProviderDao.findById(mid);
        if (serviceProvider == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        RegionPo regionPo = this.regionDao.findById(rid);
        if (regionPo == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        DraftService bo = CloneFactory.copy(new DraftService(), vo);
        bo.setStatus(0);
        bo.setService_provider_id(mid);
        bo.setRegion_id(rid);
        bo.setService_id(null);
        bo = serviceProvider.createDraftService(bo, user);
        return new ReturnObject(bo);
    }
}
