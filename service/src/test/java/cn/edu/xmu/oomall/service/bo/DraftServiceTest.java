package cn.edu.xmu.oomall.service.bo;

//import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
//import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DraftServiceTest {

    @Mock
    private DraftServiceDao draftServiceDao;

    @InjectMocks
    private DraftService draftService;

    @Test
    void setIdShouldSetId() {
        draftService.setId(1L);
        assertEquals(1L, draftService.getId());
    }

    @Test
    void setNameShouldSetName(){
        draftService.setName("维修手机");
        assertEquals("维修手机",draftService.getName());
    }

    @Test
    void setDescriptionShouldSetDescription(){
        draftService.setDescription("维修手机屏幕");
        assertEquals("维修手机屏幕",draftService.getDescription());
    }


    @Test
    void setTypeShouldSetType(){
        draftService.setType(0);
        assertEquals(0,draftService.getType());
    }

    @Test
    void setStatusShouldSetStatus(){
        draftService.setStatus(0);
        assertEquals(0,draftService.getStatus());
    }

    @Test
    void setServiceProviderIdShouldSetServiceProviderId(){
        draftService.setService_provider_id(1L);
        assertEquals(1L,draftService.getService_provider_id());
    }

    @Test
    void setServiceIdShouldSetServiceId(){
        draftService.setService_id(1L);
        assertEquals(1l,draftService.getService_id());
    }

    @Test
    void setCategoryNameShouldSetCategoryName(){
        draftService.setCategory_name("数码类");
        assertEquals("数码类",draftService.getCategory_name());
    }

    @Test
    void setRegionIdShouldSetRegionId(){
        draftService.setRegion_id(1L);
        assertEquals(1l,draftService.getRegion_id());
    }


}
