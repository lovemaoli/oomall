package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceOrderService {
    private final Logger logger = LoggerFactory.getLogger(DraftServiceService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ServiceOrderDao serviceOrderDao;

    public ServiceOrderService(ServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public ReturnNo confirmReceive(Long mid, Long billcode, UserDto user) {
        ServiceOrder serviceOrder = serviceOrderDao.findByBillCode(billcode, mid);
        if(serviceOrder == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        if(serviceOrder.getService_provider_id() != user.getDepartId()) {
            return ReturnNo.AUTH_INVALID_ACCOUNT;
        }
        ReturnNo ret = serviceOrder.confirm(user);
        return ret;
    }

    public ReturnNo serviceProviderCancelOrder(Long mid, Long id, String reason, UserDto user) {
        ServiceOrder serviceOrder = serviceOrderDao.findById(id);
        if(serviceOrder == null) {
            return ReturnNo.RESOURCE_ID_NOTEXIST;
        }
        if(serviceOrder.getService_provider_id() != user.getDepartId()) {
            return ReturnNo.AUTH_INVALID_ACCOUNT;
        }
        ReturnNo ret = serviceOrder.providerCancel(reason, user);
        return ret;
    }

}
