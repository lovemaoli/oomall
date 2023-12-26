package cn.edu.xmu.oomall.aftersale;

import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemPoTest {

    @Test
    void settingAndGettingIdShouldWorkCorrectly() {
        OrderItemPo orderItemPo = new OrderItemPo();
        orderItemPo.setId(1L);
        assertEquals(1L, orderItemPo.getId());
    }

    @Test
    void settingAndGettingLatestIdShouldWorkCorrectly() {
        OrderItemPo orderItemPo = new OrderItemPo();
        orderItemPo.setLatest_aftersale_id(1L);
        assertEquals(1L, orderItemPo.getLatest_aftersale_id());
    }

    @Test
    void settingAndGettingOrderIdShouldWorkCorrectly() {
        OrderItemPo orderItemPo = new OrderItemPo();
        orderItemPo.setOrder_id(1L);
        assertEquals(1L, orderItemPo.getOrder_id());
    }

    @Test
    void settingAndGettingQuantityShouldWorkCorrectly() {
        OrderItemPo orderItemPo = new OrderItemPo();
        orderItemPo.setQuantity(1);
        assertEquals(1, orderItemPo.getQuantity());
    }
}
