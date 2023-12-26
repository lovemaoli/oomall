package cn.edu.xmu.oomall.aftersale.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AftersaleTest {

    @Mock
    private AftersaleDao aftersaleDao;

    @Mock
    private ArbitrationDao arbitrationDao;

    @InjectMocks
    private Aftersale aftersale;

    @Test
    void createArbitrationShouldReturnArbitrationWhenAllowed() {
        UserDto user = new UserDto();
        user.setId(1L);
        aftersale.setCustomer_id(1L);
        aftersale.setStatus(Aftersale.FINISH);
        aftersale.setIn_arbitration(0);

        ReturnObject result = aftersale.createArbitration("reason", user);

        assertNotNull(result);
        assertEquals(ReturnNo.OK, result.getCode());
    }

    @Test
    void createArbitrationShouldReturnErrorWhenNotAllowed() {
        UserDto user = new UserDto();
        user.setId(1L);
        aftersale.setCustomer_id(1L);
        aftersale.setStatus(Aftersale.NEW);
        aftersale.setIn_arbitration(0);

        ReturnObject result = aftersale.createArbitration("reason", user);

        assertNotNull(result);
        assertEquals(ReturnNo.ARBITRATION_STATE_NOTALLOW, result.getCode());
    }

    @Test
    void setStatusShouldChangeStatusWhenAllowed() {
        aftersale.setStatus(Aftersale.NEW);

        assertDoesNotThrow(() -> aftersale.setStatus(Aftersale.PENDING));
        assertEquals(Aftersale.PENDING, aftersale.getStatus());
    }

    @Test
    void auditShouldReturnErrorWhenStatusIsNewAndConfirmIsTrue() {
        aftersale.setStatus(Aftersale.NEW);
        UserDto user = new UserDto();

        ReturnNo result = aftersale.audit(1L, Boolean.TRUE, "conclusion", user);

        assertEquals(ReturnNo.AFTERSALE_STATENOTALLOW, result);
    }
}