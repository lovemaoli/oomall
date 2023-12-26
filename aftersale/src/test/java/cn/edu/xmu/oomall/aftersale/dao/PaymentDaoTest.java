package cn.edu.xmu.oomall.aftersale.dao;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentDaoTest {

    @Mock
    private OrderItem orderItem;

    @InjectMocks
    private PaymentDao paymentDao;

    @Test
    void createRefundShouldReturnOk() {
        ReturnNo result = paymentDao.createRefund(orderItem, 1L);

        assertEquals(ReturnNo.OK, result);
    }
}