package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.ServiceOrderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ServiceOrderDaoTest {

    @InjectMocks
    private ServiceOrderDao serviceOrderDao;

    @Mock
    private ServiceOrderPoMapper serviceOrderPoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void findByBillCodeReturnsServiceOrderWhenPresent() {
//        ServiceOrderPo serviceOrderPo = new ServiceOrderPo();
//        when(serviceOrderPoMapper.findByBillcode(anyLong(), anyLong())).thenReturn(Optional.of(serviceOrderPo));
//
//        ServiceOrder result = serviceOrderDao.findByBillCode(1L, 1L);
//
//        assertEquals(serviceOrderPo, result);
//        verify(serviceOrderPoMapper, times(1)).findByBillcode(anyLong(), anyLong());
//    }

    @Test
    public void findByBillCodeReturnsNullWhenNotPresent() {
        when(serviceOrderPoMapper.findByBillcode(anyLong(), anyLong())).thenReturn(Optional.empty());

        ServiceOrder result = serviceOrderDao.findByBillCode(1L, 1L);

        assertNull(result);
        verify(serviceOrderPoMapper, times(1)).findByBillcode(anyLong(), anyLong());
    }

//    @Test
//    public void saveCallsSaveOnServiceOrderPoMapper() {
//        ServiceOrder serviceOrder = new ServiceOrder();
//        UserDto userDto = new UserDto();
//        doNothing().when(serviceOrderPoMapper).save(any(ServiceOrderPo.class));
//
//        serviceOrderDao.save(serviceOrder, userDto);
//
//        verify(serviceOrderPoMapper, times(1)).save(any(ServiceOrderPo.class));
//    }

//    @Test
//    public void findByIdReturnsServiceOrderWhenPresent() {
//        ServiceOrderPo serviceOrderPo = new ServiceOrderPo();
//        when(serviceOrderPoMapper.findById(anyLong())).thenReturn(Optional.of(serviceOrderPo));
//
//        ServiceOrder result = serviceOrderDao.findById(1L);
//
//        assertEquals(serviceOrderPo, result);
//        verify(serviceOrderPoMapper, times(1)).findById(anyLong());
//    }

    @Test
    public void findByIdReturnsNullWhenNotPresent() {
        when(serviceOrderPoMapper.findById(anyLong())).thenReturn(Optional.empty());

        ServiceOrder result = serviceOrderDao.findById(1L);

        assertNull(result);
        verify(serviceOrderPoMapper, times(1)).findById(anyLong());
    }
}