package cn.edu.xmu.oomall.service.service;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceOrderServiceTest {

    @InjectMocks
    private ServiceOrderService serviceOrderService;

    @Mock
    private ServiceOrderDao serviceOrderDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void confirmReceiveReturnsResourceIdNotExistWhenServiceOrderNotPresent() {
        when(serviceOrderDao.findByBillCode(anyLong(), anyLong())).thenReturn(null);

        ReturnNo result = serviceOrderService.confirmReceive(1L, 1L, new UserDto());

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result);
        verify(serviceOrderDao, times(1)).findByBillCode(anyLong(), anyLong());
    }

    @Test
    public void confirmReceiveReturnsAuthInvalidAccountWhenServiceProviderIdNotMatch() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setService_provider_id(2L);
        when(serviceOrderDao.findByBillCode(anyLong(), anyLong())).thenReturn(serviceOrder);

        UserDto user = new UserDto();
        user.setDepartId(1L);
        ReturnNo result = serviceOrderService.confirmReceive(1L, 1L, user);

        assertEquals(ReturnNo.AUTH_INVALID_ACCOUNT, result);
        verify(serviceOrderDao, times(1)).findByBillCode(anyLong(), anyLong());
    }

    @Test
    public void serviceProviderCancelOrderReturnsResourceIdNotExistWhenServiceOrderNotPresent() {
        when(serviceOrderDao.findById(anyLong())).thenReturn(null);

        ReturnNo result = serviceOrderService.serviceProviderCancelOrder(1L, 1L, "reason", new UserDto());

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result);
        verify(serviceOrderDao, times(1)).findById(anyLong());
    }

    @Test
    public void serviceProviderCancelOrderReturnsAuthInvalidAccountWhenServiceProviderIdNotMatch() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setService_provider_id(2L);
        when(serviceOrderDao.findById(anyLong())).thenReturn(serviceOrder);

        UserDto user = new UserDto();
        user.setDepartId(1L);
        ReturnNo result = serviceOrderService.serviceProviderCancelOrder(1L, 1L, "reason", user);

        assertEquals(ReturnNo.AUTH_INVALID_ACCOUNT, result);
        verify(serviceOrderDao, times(1)).findById(anyLong());
    }
}