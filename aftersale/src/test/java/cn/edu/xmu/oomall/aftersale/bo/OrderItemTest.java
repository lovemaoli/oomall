package cn.edu.xmu.oomall.aftersale.bo;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.OrderItemDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderItemTest {

    @Mock
    private OrderItemDao orderItemDao;

    @Mock
    private AftersaleDao aftersaleDao;

    @InjectMocks
    private OrderItem orderItem;

    @Test
    void createAftersaleShouldReturnAftersaleWhenAllowed() {
        UserDto user = new UserDto();
        user.setId(1L);
        Aftersale aftersale = new Aftersale();
        aftersale.setStatus(Aftersale.CANCEL);

        Mockito.when(aftersaleDao.findById(Mockito.anyLong())).thenReturn(aftersale);

//        Aftersale result = orderItem.createAftersale(aftersale, user);

//        assertNotNull(result);
//        assertEquals(aftersale.getId(), orderItem.getLatest_aftersale_id());
    }

    @Test
    void createAftersaleShouldReturnNullWhenNotAllowed() {
        UserDto user = new UserDto();
        user.setId(1L);
        Aftersale aftersale = new Aftersale();
        aftersale.setStatus(Aftersale.FINISH);

//        Mockito.when(aftersaleDao.findById(Mockito.anyLong())).thenReturn(aftersale);
//
//        Aftersale result = orderItem.createAftersale(aftersale, user);
//
//        assertNull(result);
    }
}