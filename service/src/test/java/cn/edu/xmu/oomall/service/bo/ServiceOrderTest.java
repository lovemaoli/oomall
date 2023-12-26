package cn.edu.xmu.oomall.service.bo;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ExpressDao;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceOrderTest {

    @InjectMocks
    private ServiceOrder serviceOrder;

    @Mock
    private ServiceOrderDao serviceOrderDao;

    @Mock
    private ExpressDao expressDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void confirmReturnsStateNotAllowedWhenStatusNotUnprocessed() {
        serviceOrder.setStatus(ServiceOrder.PROCESSING);
        UserDto user = new UserDto();

        ReturnNo result = serviceOrder.confirm(user);

        assertEquals(ReturnNo.STATENOTALLOW, result);
    }

//    @Test
//    void confirmReturnsOkWhenStatusIsUnprocessed() {
//        serviceOrder.setStatus(ServiceOrder.UNPROCESSED);
//        UserDto user = new UserDto();
//
//        ReturnNo result = serviceOrder.confirm(user);
//
//        assertEquals(ReturnNo.OK, result);
//        verify(serviceOrderDao, times(1)).save(serviceOrder, user);
//    }

    @Test
    void providerCancelReturnsStateNotAllowedWhenStatusNotProcessing() {
        serviceOrder.setStatus(ServiceOrder.NEW);
        UserDto user = new UserDto();

        ReturnNo result = serviceOrder.providerCancel("reason", user);

        assertEquals(ReturnNo.STATENOTALLOW, result);
    }

//    @Test
//    void providerCancelReturnsAuthNoRightWhenServiceProviderIdNotMatch() {
//        serviceOrder.setStatus(ServiceOrder.PROCESSING);
//        serviceOrder.setService_provider_id(1L);
//        UserDto user = new UserDto();
//        user.setDepartId(2L);
//
//        ReturnNo result = serviceOrder.providerCancel("reason", user);
//
//        assertEquals(ReturnNo.AUTH_NO_RIGHT, result);
//    }

//    @Test
//    void providerCancelReturnsOkWhenStatusIsProcessingAndServiceProviderIdMatch() {
//        serviceOrder.setStatus(ServiceOrder.PROCESSING);
//        serviceOrder.setService_provider_id(1L);
//        UserDto user = new UserDto();
//        user.setDepartId(1L);
//
//        ReturnNo result = serviceOrder.providerCancel("reason", user);
//
//        assertEquals(ReturnNo.OK, result);
//        verify(serviceOrderDao, times(1)).save(serviceOrder, user);
//    }
}