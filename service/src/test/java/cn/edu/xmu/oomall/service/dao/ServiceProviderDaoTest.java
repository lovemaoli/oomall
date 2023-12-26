package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.ServiceProviderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ServiceProviderDaoTest {

    @InjectMocks
    private ServiceProviderDao serviceProviderDao;

    @Mock
    private ServiceProviderPoMapper serviceProviderPoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createServiceProviderReturnsServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        ServiceProviderPo serviceProviderPo = new ServiceProviderPo();
        when(serviceProviderPoMapper.save(any())).thenReturn(serviceProviderPo);

        ServiceProvider result = serviceProviderDao.createServiceProvider(serviceProvider);

        assertEquals(serviceProvider, result);
        verify(serviceProviderPoMapper, times(1)).save(any());
    }

    @Test
    public void updateServiceProviderReturnsUpdatedServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        ServiceProviderPo serviceProviderPo = new ServiceProviderPo();
        when(serviceProviderPoMapper.save(any())).thenReturn(serviceProviderPo);

        ServiceProvider result = serviceProviderDao.updateServiceProvider(serviceProvider);

        assertEquals(serviceProvider, result);
        verify(serviceProviderPoMapper, times(1)).save(any());
    }

    @Test
    public void deleteServiceProviderReturnsOK() {
        doNothing().when(serviceProviderPoMapper).deleteById(anyLong());

        ReturnNo result = serviceProviderDao.deleteServiceProvider(1L);

        assertEquals(ReturnNo.OK, result);
        verify(serviceProviderPoMapper, times(1)).deleteById(anyLong());
    }

    @Test
    public void changeServiceProviderStatusReturnsOKWhenPresent() {
        ServiceProviderPo serviceProviderPo = new ServiceProviderPo();
        when(serviceProviderPoMapper.findById(anyLong())).thenReturn(Optional.of(serviceProviderPo));

        ReturnNo result = serviceProviderDao.changeServiceProviderStatus(1L, 1);

        assertEquals(ReturnNo.OK, result);
        verify(serviceProviderPoMapper, times(1)).findById(anyLong());
    }

    @Test
    public void changeServiceProviderStatusReturnsResourceIdNotExistWhenNotPresent() {
        when(serviceProviderPoMapper.findById(anyLong())).thenReturn(Optional.empty());

        ReturnNo result = serviceProviderDao.changeServiceProviderStatus(1L, 1);

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result);
        verify(serviceProviderPoMapper, times(1)).findById(anyLong());
    }

//    @Test
//    public void findByIdReturnsServiceProviderWhenPresent() {
//        ServiceProviderPo serviceProviderPo = new ServiceProviderPo();
//        when(serviceProviderPoMapper.findById(anyLong())).thenReturn(Optional.of(serviceProviderPo));
//
//        ServiceProvider result = serviceProviderDao.findById(1L);
//
//        assertEquals(serviceProviderPo, result);
//        verify(serviceProviderPoMapper, times(1)).findById(anyLong());
//    }

    @Test
    public void findByIdReturnsNullWhenNotPresent() {
        when(serviceProviderPoMapper.findById(anyLong())).thenReturn(Optional.empty());

        ServiceProvider result = serviceProviderDao.findById(1L);

        assertNull(result);
        verify(serviceProviderPoMapper, times(1)).findById(anyLong());
    }
}