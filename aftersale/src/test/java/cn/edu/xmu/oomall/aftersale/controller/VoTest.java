package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.oomall.aftersale.controller.vo.AftersaleVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApplyAftersaleVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationRequestVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ShopConfirmVo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VoTest {

    @Test
    public void testAftersaleVo() {
        AftersaleVo vo = new AftersaleVo();
        vo.setType(1);
        vo.setReason("reason");
        vo.setConclusion("conclusion");
        vo.setQuantity(1);
        vo.setContact("contact");
        vo.setMobile("mobile");
        vo.setAddress("address");
        vo.setStatus(1);
        vo.setOrder_item_id(1L);
        vo.setProduct_item_id(1L);
        vo.setShop_id(1L);
        vo.setArbitration_id(1L);
        vo.setCustomer_id(1L);
        vo.setRefund_trans_id(1L);

        assertEquals(1, vo.getType());
        assertEquals("reason", vo.getReason());
        assertEquals("conclusion", vo.getConclusion());
        assertEquals(1, vo.getQuantity());
        assertEquals("contact", vo.getContact());
        assertEquals("mobile", vo.getMobile());
        assertEquals("address", vo.getAddress());
        assertEquals(1, vo.getStatus());
        assertEquals(1L, vo.getOrder_item_id());
        assertEquals(1L, vo.getProduct_item_id());
        assertEquals(1L, vo.getShop_id());
        assertEquals(1L, vo.getArbitration_id());
        assertEquals(1L, vo.getCustomer_id());
        assertEquals(1L, vo.getRefund_trans_id());
    }

    @Test
    public void testArbitrationRequestVo() {
        ArbitrationRequestVo vo = new ArbitrationRequestVo();
        vo.setReason("reason");

        assertEquals("reason", vo.getReason());
    }

    @Test
    public void testApplyAftersaleVo() {
        ApplyAftersaleVo vo = new ApplyAftersaleVo();
        vo.setType(1);
        vo.setReason("reason");
        vo.setContact("contact");
        vo.setMobile("mobile");
        vo.setAddress("address");
        vo.setProduct_id(1L);
        vo.setProduct_item_id(1L);

        assertEquals(1, vo.getType());
        assertEquals("reason", vo.getReason());
        assertEquals("contact", vo.getContact());
        assertEquals("mobile", vo.getMobile());
        assertEquals("address", vo.getAddress());
        assertEquals(1L, vo.getProduct_id());
        assertEquals(1L, vo.getProduct_item_id());
    }

    @Test
    public void testArbitrationVo() {
        ArbitrationVo vo = new ArbitrationVo();
        vo.setReason("reason");
        vo.setShop_reason("shop_reason");
        vo.setResult("result");
        vo.setArbitrator("arbitrator");
        vo.setGmt_arbitration(LocalDateTime.now());
        vo.setGmt_accept(LocalDateTime.now());
        vo.setGmt_apply(LocalDateTime.now());
        vo.setGmt_reply(LocalDateTime.now());
        vo.setShop_id(1L);
        vo.setAftersale_id(1L);
        vo.setCustomer_id(1L);

        assertEquals("reason", vo.getReason());
        assertEquals("shop_reason", vo.getShop_reason());
        assertEquals("result", vo.getResult());
        assertEquals("arbitrator", vo.getArbitrator());
        assertNotNull(vo.getGmt_arbitration());
        assertNotNull(vo.getGmt_accept());
        assertNotNull(vo.getGmt_apply());
        assertNotNull(vo.getGmt_reply());
        assertEquals(1L, vo.getShop_id());
        assertEquals(1L, vo.getAftersale_id());
        assertEquals(1L, vo.getCustomer_id());
    }

    @Test
    public void testShopConfirmVo() {
        ShopConfirmVo vo = new ShopConfirmVo();
        vo.setConfirm(true);
        vo.setConclusion("conclusion");

        assertTrue(vo.getConfirm());
        assertEquals("conclusion", vo.getConclusion());
    }
}