package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.oomall.service.service.DraftServiceService;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.dao.RegionDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DraftServiceServiceTest {

    @InjectMocks
    private DraftServiceService draftServiceService;

    @Mock
    private DraftServiceDao draftServiceDao;

    @Mock
    private ServiceProviderDao serviceProviderDao;

    @Mock
    private RegionDao regionDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void defServiceForProductInRegionReturnsResourceNotExistWhenServiceProviderNotPresent() {
        when(serviceProviderDao.findById(anyLong())).thenReturn(null);

        ReturnObject result = draftServiceService.defServiceForProductInRegion(1L, 1L, new DraftService(), new UserDto());

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result.getCode());
        verify(serviceProviderDao, times(1)).findById(anyLong());
    }

    @Test
    public void defServiceForProductInRegionReturnsResourceNotExistWhenRegionNotPresent() {
        when(serviceProviderDao.findById(anyLong())).thenReturn(new ServiceProvider());
        when(regionDao.findById(anyLong())).thenReturn(null);

        ReturnObject result = draftServiceService.defServiceForProductInRegion(1L, 1L, new DraftService(), new UserDto());

        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, result.getCode());
        verify(serviceProviderDao, times(1)).findById(anyLong());
        verify(regionDao, times(1)).findById(anyLong());
    }

}
