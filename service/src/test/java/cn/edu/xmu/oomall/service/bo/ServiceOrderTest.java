package cn.edu.xmu.oomall.service.bo;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ExpressDao;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceOrderTest {
    @Mock
    private ServiceOrderDao serviceOrderDao;

    @Mock
    private ExpressDao expressDao;

    @InjectMocks
    private ServiceOrder serviceOrder;

    @Test
    void setIdShouldSetId() {
        serviceOrder.setId(1L);
        assertEquals(1L, serviceOrder.getId());
    }


}