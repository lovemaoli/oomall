package cn.edu.xmu.oomall.service.bo;


import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.Service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {

    @Mock
    private ServiceDao serviceDao;

    @InjectMocks
    private Service service;

    @Test
    void setIdShouldSetId() {
        service.setId(1L);
        assertEquals(1L, service.getId());
    }

    @Test
    void setNameShouldSetName(){
        service.setName("维修电脑");
        assertEquals("维修电脑",service.getName());
    }

    @Test
    void setDescriptionShouldSetDescription(){
        service.setDescription("维修电脑硬件故障");
        assertEquals("维修电脑硬件故障",service.getDescription());
    }

    @Test
    void setTypeShouldSetType(){
        service.setType(1);
        assertEquals(1,service.getType());
    }

    @Test
    void setServiceAreaShouldSetServiceArea(){
        service.setService_area("福建省厦门市翔安区");
        assertEquals("福建省厦门市翔安区",service.getService_area());
    }

    @Test
    void setCategoryNameShouldSetCategoryName(){
        service.setCategory_name("数码类");
        assertEquals("数码类",service.getCategory_name());
    }

    @Test
    void setStatusShouldSetStatus(){
        service.setStatus(0);
        assertEquals(0,service.getStatus());
    }

    @Test
    void setServiceProviderIdShouldSetServiceProviderId(){
        service.setService_provider_id(1L);
        assertEquals(1L,service.getService_provider_id());
    }

    @Test
    void setRegionIdShouldSetRegionId(){
        service.setRegion_id(1L);
        assertEquals(1l,service.getRegion_id());
    }

    @Test
    void changeStatusShouldChangeStatusWhenAllowed(){
        service.setStatus(Service.INVALID);
        assertDoesNotThrow(() -> service.changeStatus(Service.VALID));
        assertEquals(Service.VALID, service.getStatus());
    }

}
