package cn.edu.xmu.oomall.service.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProviderStatus;
import cn.edu.xmu.oomall.service.dao.bo.ShopService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ShopServiceTest {
    @Mock
    private ServiceDao serviceDao;

    @Mock
    private ShopService shopServiceDao;
    @InjectMocks
    private ShopService shopService;
    @Test
    void changeStatusShouldChangeStatusWhenAllowed() {
        shopService.setShop_status((ShopService.VALID));

        assertDoesNotThrow(() -> shopService.changeStatus(ShopService.SHOPSUSPEND));
        assertEquals(ShopService.SHOPSUSPEND, shopService.getShop_status());
    }
    @Test
    void changeStatusWhenServiceProviderStatusAllowed() {
        shopService.setShop_status((ShopService.VALID));
        shopService.setStatus((ServiceProviderStatus.SUSPEND));

        assertDoesNotThrow(() -> shopService.changeStatus(ShopService.PLATSUSPENDVALID));
        assertEquals(ShopService.PLATSUSPENDVALID, shopService.getShop_status());
    }

    @Test
    void setIdShouldSetId() {
        shopService.setId(1L);
        assertEquals(1L, shopService.getId());
    }
    @Test
    void setIdShouldSetName() {
        shopService.setName("test");
        assertEquals("test", shopService.getName());
    }
    @Test
    void setIdShouldSetShopId() {
        shopService.setShop_id(1L);
        assertEquals(1L, shopService.getShop_id());
    }
    @Test
    void setIdShouldSetServiceId() {
        shopService.setService_id(1L);
        assertEquals(1L, shopService.getService_id());
    }

    @Test
    void setIdShouldSetProductId() {
        shopService.setProduct_id(1L);
        assertEquals(1L, shopService.getProduct_id());
    }
    @Test
    void setIdShouldSetServiceProviderId() {
        shopService.setService_provider_id(1l);
        assertEquals(1L, shopService.getService_provider_id());
    }

}