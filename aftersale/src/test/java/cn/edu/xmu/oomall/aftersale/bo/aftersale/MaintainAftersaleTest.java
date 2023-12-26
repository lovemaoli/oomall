package cn.edu.xmu.oomall.aftersale.bo.aftersale;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.aftersale.MaintainAftersale;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MaintainAftersaleTest {

    @Mock
    private Aftersale aftersale;

    @InjectMocks
    private MaintainAftersale maintainAftersale;

//    @Test
//    void shopAuditShouldReturnOkWhenShopIdMatches() {
//        when(aftersale.getShop_id()).thenReturn(1L);
//        ReturnNo result = maintainAftersale.shopAudit(1L);
//        assertEquals(ReturnNo.OK, result);
//    }
//
//    @Test
//    void shopAuditShouldReturnAuthNoRightWhenShopIdDoesNotMatch() {
//        when(aftersale.getShop_id()).thenReturn(1L);
//        ReturnNo result = maintainAftersale.shopAudit(2L);
//        assertEquals(ReturnNo.AUTH_NO_RIGHT, result);
//    }
}