package cn.edu.xmu.oomall.service.bo;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProviderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceProviderTest {
    @Mock
    private ServiceProviderDao serviceProviderDao;

    @Mock
    private DraftServiceDao draftServiceDao;

    @Mock
    private ShopServiceDao shopServiceDao;

    @Mock
    private ServiceDao serviceDao;

    @InjectMocks
    private ServiceProvider serviceProvider;

    @Test
    void setIdShouldSetId() {
        serviceProvider.setId(1L);
        assertEquals(1L, serviceProvider.getId());
    }

    @Test
    void setNameShouldSetName() {
        serviceProvider.setName("Test");
        assertEquals("Test", serviceProvider.getName());
    }

    @Test
    void setConsigneeShouldSetConsignee() {
        serviceProvider.setConsignee("Test");
        assertEquals("Test", serviceProvider.getConsignee());
    }

    @Test
    void setAddressShouldSetAddress() {
        serviceProvider.setAddress("Test");
        assertEquals("Test", serviceProvider.getAddress());
    }

    @Test
    void setMobileShouldSetMobile() {
        serviceProvider.setMobile("Test");
        assertEquals("Test", serviceProvider.getMobile());
    }

    @Test
    void setServiceMaxNumShouldSetServiceMaxNum() {
        serviceProvider.setService_max_num(10);
        assertEquals(10, serviceProvider.getService_max_num());
    }

    @Test
    void setDepositShouldSetDeposit() {
        serviceProvider.setDeposit(100);
        assertEquals(100, serviceProvider.getDeposit());
    }

    @Test
    void setDepositThresholdShouldSetDepositThreshold() {
        serviceProvider.setDeposit_threshold(100);
        assertEquals(100, serviceProvider.getDeposit_threshold());
    }

    @Test
    void setStatusShouldSetStatus() {
        serviceProvider.setStatus(ServiceProvider.VALID);
        assertEquals(ServiceProvider.VALID, serviceProvider.getStatus());
    }

    @Test
    void changeStatusShouldChangeStatusWhenAllowed() {
        serviceProvider.setStatus(ServiceProvider.APPLY);
        assertEquals(ReturnNo.OK, serviceProvider.changeStatus(ServiceProviderStatus.VALID));
        assertEquals(ServiceProvider.VALID, serviceProvider.getStatus());
    }

    @Test
    void changeStatusShouldReturnStateNotAllowWhenNotAllowed() {
        serviceProvider.setStatus(ServiceProvider.APPLY);
        assertEquals(ReturnNo.SERVICE_PROVIDER_STATE_NOTALLOW, serviceProvider.changeStatus(ServiceProviderStatus.BANNED));
        assertEquals(ServiceProvider.APPLY, serviceProvider.getStatus());
    }
}