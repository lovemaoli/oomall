package cn.edu.xmu.oomall.service.service;
import cn.edu.xmu.oomall.service.service.ServiceProviderService;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceProviderServiceTest {

    @InjectMocks
    private ServiceProviderService serviceProviderService;

    @Mock
    private ServiceProviderDao serviceProviderDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByIdReturnsServiceProviderWhenPresent() {
        ServiceProvider serviceProvider = new ServiceProvider();
        when(serviceProviderDao.findById(anyLong())).thenReturn(serviceProvider);

        ServiceProvider result = serviceProviderService.findById(1L);

        assertEquals(serviceProvider, result);
        verify(serviceProviderDao, times(1)).findById(anyLong());
    }

    @Test
    public void deleteServiceProviderReturnsOKWhenServiceProviderPresent() {
        when(serviceProviderDao.deleteServiceProvider(anyLong())).thenReturn(ReturnNo.OK);

        ReturnNo result = serviceProviderService.deleteServiceProvider(1L);

        assertEquals(ReturnNo.OK, result);
        verify(serviceProviderDao, times(1)).deleteServiceProvider(anyLong());
    }

//    @Test
//    public void changeServiceProviderStatusReturnsOKWhenServiceProviderPresent() {
//        ServiceProvider serviceProvider = new ServiceProvider();
//        when(serviceProviderDao.findById(anyLong())).thenReturn(serviceProvider);
//        when(serviceProvider.changeStatus(any())).thenReturn(ReturnNo.OK);
//
//        ReturnNo result = serviceProviderService.changeServiceProviderStatus(1L, 1);
//
//        assertEquals(ReturnNo.OK, result);
//        verify(serviceProviderDao, times(1)).findById(anyLong());
//    }

    @Test
    public void changeServiceProviderStatusReturnsResourceIdNotExistWhenServiceProviderNotPresent() {
        when(serviceProviderDao.findById(anyLong())).thenReturn(null);

        ReturnNo result = serviceProviderService.changeServiceProviderStatus(1L, 1);

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result);
        verify(serviceProviderDao, times(1)).findById(anyLong());
    }
}