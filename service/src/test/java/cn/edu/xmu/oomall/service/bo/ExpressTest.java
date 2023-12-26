package cn.edu.xmu.oomall.service.bo;

//import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ExpressDao;
//import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.bo.Express;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpressTest {
    @Mock
    private ExpressDao expressDao;

    @InjectMocks
    private Express express;

    @Test
    void setIdShouldSetId() {
        express.setId(1L);
        assertEquals(1L, express.getId());
    }

    @Test
    void setServiceOrderIdshouldSetServiceOrderId(){
        express.setService_order_id(1L);
        assertEquals(1L,express.getService_order_id());
    }

    @Test
    void setBillCodeShouldSetBillCode(){
        express.setBill_code(1234567890L);
        assertEquals(1234567890L,express.getBill_code());
    }

}
