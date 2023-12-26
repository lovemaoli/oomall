package cn.edu.xmu.oomall.aftersale.bo;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArbitrationTest {

    @Mock
    private ArbitrationDao arbitrationDao;

    @Mock
    private AftersaleDao aftersaleDao;

    @InjectMocks
    private Arbitration arbitration;


    @Test
    void cancelShouldReturnStateNotAllowedWhenStatusIsSuccess() {
        arbitration.setStatus(Arbitration.SUCCESS);

        ReturnNo result = arbitration.cancel();

        assertEquals(ReturnNo.STATENOTALLOW, result);
        assertEquals(Arbitration.SUCCESS, arbitration.getStatus());
    }

    @Test
    void changeStatusShouldChangeStatusWhenAllowed() {
        arbitration.setStatus(Arbitration.NEW);

        assertDoesNotThrow(() -> arbitration.changeStatus(Arbitration.RESPOND));
        assertEquals(Arbitration.RESPOND, arbitration.getStatus());
    }

}