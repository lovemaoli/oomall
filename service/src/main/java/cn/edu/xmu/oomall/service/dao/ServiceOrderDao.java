package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.ServiceOrderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RefreshScope
public class ServiceOrderDao {
    private final static Logger logger = LoggerFactory.getLogger(ServiceProviderDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ServiceOrderPoMapper serviceOrderPoMapper;

    public ServiceOrderDao(ServiceOrderPoMapper serviceOrderPoMapper) {
        this.serviceOrderPoMapper = serviceOrderPoMapper;
    }

    public ServiceOrder build(ServiceOrderPo po){
        return CloneFactory.copy(new ServiceOrder(), po);
    }

    public ServiceOrder findByBillCode(Long billCode, Long ServiceProviderId) {
        Optional<ServiceOrderPo> po = serviceOrderPoMapper.findByBillcode(billCode, ServiceProviderId);
        if(po.isPresent()) {
            return build(po.get());
        }
        return null;
    }

    public void save(ServiceOrder serviceOrder) {
        ServiceOrderPo po = CloneFactory.copy(new ServiceOrderPo(), serviceOrder);
        serviceOrderPoMapper.save(po);
    }
}
