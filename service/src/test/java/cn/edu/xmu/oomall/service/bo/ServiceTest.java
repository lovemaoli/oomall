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
    void settingAndGettingIdShouldWorkCorrectly() {
        service.setId(1L);
        assertEquals(1L, service.getId());
    }

    @Test
    void settingAndGettingNameShouldWorkCorrectly() {
        service.setName("Test");
        assertEquals("Test", service.getName());
    }

    @Test
    void settingAndGettingDescriptionShouldWorkCorrectly() {
        service.setDescription("Test");
        assertEquals("Test", service.getDescription());
    }

    @Test
    void settingAndGettingTypeShouldWorkCorrectly() {
        service.setType(1);
        assertEquals(1, service.getType());
    }

    @Test
    void settingAndGettingServiceAreaShouldWorkCorrectly() {
        service.setService_area("Test");
        assertEquals("Test", service.getService_area());
    }

    @Test
    void settingAndGettingCategoryNameShouldWorkCorrectly() {
        service.setCategory_name("Test");
        assertEquals("Test", service.getCategory_name());
    }

    @Test
    void settingAndGettingStatusShouldWorkCorrectly() {
        service.setStatus(1);
        assertEquals(1, service.getStatus());
    }

    @Test
    void settingAndGettingServiceProviderIdShouldWorkCorrectly() {
        service.setService_provider_id(1L);
        assertEquals(1L, service.getService_provider_id());
    }

    @Test
    void settingAndGettingRegionIdShouldWorkCorrectly() {
        service.setRegion_id(1L);
        assertEquals(1L, service.getRegion_id());
    }

    @Test
    void changeStatusShouldChangeStatusWhenAllowed() {
        service.setStatus(Service.VALID);
        service.setServiceDao(serviceDao);

        assertDoesNotThrow(() -> service.changeStatus(Service.INVALID));
        assertEquals(Service.INVALID, service.getStatus());

        Mockito.verify(serviceDao, Mockito.times(1)).save(service);
    }
}