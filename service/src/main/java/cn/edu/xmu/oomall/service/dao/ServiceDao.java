package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.bo.ShopService;
import cn.edu.xmu.oomall.service.mapper.ShopServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import cn.edu.xmu.oomall.service.mapper.po.ServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RefreshScope
public class ServiceDao {

    private final static Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ServicePoMapper servicePoMapper;

    public ServiceDao(ServicePoMapper servicePoMapper) {
        this.servicePoMapper = servicePoMapper;
    }

    public Service build(ServicePo po){
        return CloneFactory.copy(new Service(), po);
    }

    public List<Service> findByServiceProviderId(Long id) {
        List<ServicePo> services = this.servicePoMapper.findByServiceProviderId(id);
        List<Service> serviceList = new java.util.ArrayList<>(services.size());
        for (ServicePo po : services) {
            Service service = build(po);
            serviceList.add(service);
        }
        return serviceList;
    }

    public void save(Service service) {
        ServicePo po = CloneFactory.copy(new ServicePo(), service);
        this.servicePoMapper.save(po);
    }
}
