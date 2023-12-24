package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.service.controller.dto.ServiceProviderDto;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceProviderService {

    private final Logger logger = LoggerFactory.getLogger(ServiceProviderService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ServiceProviderDao serviceProviderDao;

    @Autowired
    public ServiceProviderService(ServiceProviderDao serviceProviderDao) {
        this.serviceProviderDao = serviceProviderDao;
    }

    public ServiceProvider findById(Long id) {
        return this.serviceProviderDao.findById(id);
    }




    public ServiceProviderDto createServiceProvider(ServiceProviderDto serviceProviderDto) {
        ServiceProvider serviceProvider = CloneFactory.copy(new ServiceProvider(), serviceProviderDto);
        serviceProvider = this.serviceProviderDao.createServiceProvider(serviceProvider);
        serviceProviderDto = CloneFactory.copy(new ServiceProviderDto(), serviceProvider);
        return serviceProviderDto;
    }

    public ServiceProviderDto updateServiceProvider(ServiceProviderDto serviceProviderDto) {
        ServiceProvider serviceProvider = CloneFactory.copy(new ServiceProvider(), serviceProviderDto);
        serviceProvider = this.serviceProviderDao.updateServiceProvider(serviceProvider);
        serviceProviderDto = CloneFactory.copy(new ServiceProviderDto(), serviceProvider);
        return serviceProviderDto;
    }

    public ReturnNo deleteServiceProvider(Long id) {
        return this.serviceProviderDao.deleteServiceProvider(id);
    }

    public ReturnNo changeServiceProviderStatus(Long id, Integer state) {
        ServiceProvider serviceProvider = this.serviceProviderDao.findById(id);
        if (serviceProvider == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        return serviceProvider.changeStatus(state);
    }

    public ServiceProviderDto findServiceProviderById(Long id) {
        ServiceProvider serviceProvider = this.serviceProviderDao.findById(id);
        ServiceProviderDto dto = CloneFactory.copy(new ServiceProviderDto(), serviceProvider);
        return dto;
    }

}
