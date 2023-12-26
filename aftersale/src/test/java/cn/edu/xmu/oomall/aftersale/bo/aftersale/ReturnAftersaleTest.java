package cn.edu.xmu.oomall.aftersale.bo.aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.ReturnAftersale;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReturnAftersaleTest {

    @Mock
    private Aftersale aftersale;

    @InjectMocks
    private ReturnAftersale returnAftersale;

//    @Test
//    void examineShouldReturnOkWhenShopIdMatches() {
//        when(aftersale.getShop_id()).thenReturn(1L);
//        ReturnNo result = returnAftersale.examine(1L, true);
//        assertEquals(ReturnNo.OK, result);
//    }
//
//    @Test
//    void examineShouldReturnAuthNoRightWhenShopIdDoesNotMatch() {
//        when(aftersale.getShop_id()).thenReturn(1L);
//        ReturnNo result = returnAftersale.examine(2L, true);
//        assertEquals(ReturnNo.AUTH_NO_RIGHT, result);
//    }
//
//    @Test
//    void shopAuditShouldAlwaysReturnOk() {
//        ReturnNo result = returnAftersale.shopAudit(1L);
//        assertEquals(ReturnNo.OK, result);
//    }
}