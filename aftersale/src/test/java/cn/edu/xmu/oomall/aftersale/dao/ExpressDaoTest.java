package cn.edu.xmu.oomall.aftersale.dao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpressDaoTest {

    @InjectMocks
    private ExpressDao expressDao;

    @Test
    void createExpressShouldReturnNonNullValue() {
        Long result = expressDao.createExpress(1L);

        assertNotNull(result);
    }

    @Test
    void createExpressShouldReturnDifferentValuesForDifferentCalls() {
        Long result1 = expressDao.createExpress(1L);
        Long result2 = expressDao.createExpress(1L);

        assertNotEquals(result1, result2);
    }
}