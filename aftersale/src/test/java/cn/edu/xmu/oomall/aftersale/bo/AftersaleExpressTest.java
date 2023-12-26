package cn.edu.xmu.oomall.aftersale.bo;

import cn.edu.xmu.oomall.aftersale.dao.AftersaleExpressDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.AftersaleExpress;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AftersaleExpressTest {

    @Mock
    private AftersaleExpressDao aftersaleExpressDao;

    @InjectMocks
    private AftersaleExpress aftersaleExpress;

    @Test
    void setIdShouldSetId() {
        aftersaleExpress.setId(1L);
        assertEquals(1L, aftersaleExpress.getId());
    }

    @Test
    void setAftersaleIdShouldSetAftersaleId() {
        aftersaleExpress.setAftersale_id(1L);
        assertEquals(1L, aftersaleExpress.getAftersale_id());
    }

    @Test
    void setBillCodeShouldSetBillCode() {
        aftersaleExpress.setBill_code(1234567890L);
        assertEquals(1234567890L, aftersaleExpress.getBill_code());
    }

    @Test
    void setSenderShouldSetSender() {
        aftersaleExpress.setSender(1);
        assertEquals(1, aftersaleExpress.getSender());
    }

    @Test
    void setStatusShouldSetStatus() {
        aftersaleExpress.setStatus(1);
        assertEquals(1, aftersaleExpress.getStatus());
    }

    @Test
    void setExpressIdShouldSetExpressId() {
        aftersaleExpress.setExpress_id(1L);
        assertEquals(1L, aftersaleExpress.getExpress_id());
    }
}