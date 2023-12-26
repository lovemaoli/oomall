package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.OrderItemDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AftersaleServiceTest {

    @Mock
    private AftersaleDao aftersaleDao;

    @Mock
    private OrderItemDao orderItemDao;

    @InjectMocks
    private AftersaleService aftersaleService;

//    @Test
//    void applyAftersaleShouldReturnAftersaleWhenOrderItemExists() {
//        Long orderId = 1L;
//        Long orderItemId = 1L;
//        Aftersale aftersale = new Aftersale();
//        UserDto user = new UserDto();
//
//        OrderItem orderItem = new OrderItem();
//        when(orderItemDao.findById(orderId, orderItemId)).thenReturn(orderItem);
//        when(orderItem.createAftersale(aftersale, user)).thenReturn(aftersale);
//
//        assertEquals(aftersale, aftersaleService.applyAftersale(orderId, orderItemId, aftersale, user).getData());
//    }
//
//    @Test
//    void applyAftersaleShouldReturnResourceNotFoundWhenOrderItemDoesNotExist() {
//        Long orderId = 1L;
//        Long orderItemId = 1L;
//        Aftersale aftersale = new Aftersale();
//        UserDto user = new UserDto();
//
//        when(orderItemDao.findById(orderId, orderItemId)).thenReturn(null);
//
//        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, aftersaleService.applyAftersale(orderId, orderItemId, aftersale, user).getCode());
//    }
//
//    @Test
//    void shopReceiveShouldReturnResourceNotFoundWhenAftersaleDoesNotExist() {
//        Long billcode = 1L;
//        Long shopid = 1L;
//        Boolean confirm = true;
//        String conclusion = "conclusion";
//        UserDto user = new UserDto();
//
//        when(aftersaleDao.findByBillCode(billcode, shopid)).thenReturn(null);
//
//        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, aftersaleService.shopReceive(billcode, shopid, confirm, conclusion, user));
//    }
//
//    @Test
//    void auditAftersaleShouldReturnResourceNotFoundWhenAftersaleDoesNotExist() {
//        Long aid = 1L;
//        Long shopid = 1L;
//        Boolean confirm = true;
//        String conclusion = "conclusion";
//        UserDto user = new UserDto();
//
//        when(aftersaleDao.findById(aid)).thenReturn(null);
//
//        assertEquals(ReturnNo.RESOURCE_ID_NOTEXIST, aftersaleService.auditAftersale(aid, shopid, confirm, conclusion, user));
//    }
}