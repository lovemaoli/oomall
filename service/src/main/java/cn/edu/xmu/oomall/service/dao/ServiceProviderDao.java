package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.ServiceProviderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
public class ServiceProviderDao {

    private final static Logger logger = LoggerFactory.getLogger(ServiceProviderDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ServiceProviderPoMapper serviceProviderPoMapper;

    public ServiceProviderDao(ServiceProviderPoMapper serviceProviderPoMapper) {
        this.serviceProviderPoMapper = serviceProviderPoMapper;
    }

    public ServiceProvider build(ServiceProviderPo po){
        return CloneFactory.copy(new ServiceProvider(), po);
    }

    public ServiceProvider createServiceProvider(ServiceProvider serviceProvider) {
        ServiceProviderPo po = CloneFactory.copy(new ServiceProviderPo(), serviceProvider);
        serviceProviderPoMapper.save(po);
        return serviceProvider;
    }

    public ServiceProvider updateServiceProvider(ServiceProvider serviceProvider) {
        ServiceProviderPo po = CloneFactory.copy(new ServiceProviderPo(), serviceProvider);
        serviceProviderPoMapper.save(po);
        return serviceProvider;
    }

    public ReturnNo deleteServiceProvider(Long id) {
        serviceProviderPoMapper.deleteById(id);
        return ReturnNo.OK;
    }

    public ReturnNo changeServiceProviderStatus(Long id, Integer state) {
        Optional<ServiceProviderPo> serviceProviderPo = this.serviceProviderPoMapper.findById(id);
        if (serviceProviderPo.isPresent()) {
            ServiceProvider bo = this.build(serviceProviderPo.get());
            bo.setStatus(state);
            this.updateServiceProvider(bo);
            return ReturnNo.OK;
        } else {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
    }

    public ServiceProvider findById(Long id) {
        Optional<ServiceProviderPo> serviceProviderPo = this.serviceProviderPoMapper.findById(id);
        if (serviceProviderPo.isPresent()) {
            ServiceProvider bo = this.build(serviceProviderPo.get());
        } else {
            return null;
        }
    }


}
