package cn.edu.xmu.oomall.aftersale;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AftersalePoTest {

    @Test
    void settingAndGettingIdShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setId(1L);
        assertEquals(1L, aftersalePo.getId());
    }

    @Test
    void settingAndGettingTypeShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setType(0);
        assertEquals(0, aftersalePo.getType());
    }

    @Test
    void settingAndGettingConclusionShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setConclusion("Test");
        assertEquals("Test", aftersalePo.getConclusion());
    }

    @Test
    void settingAndGettingContactShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setContact("Test");
        assertEquals("Test", aftersalePo.getContact());
    }

    @Test
    void settingAndGettingReasonShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setReason("Test");
        assertEquals("Test", aftersalePo.getReason());
    }

    @Test
    void settingAndGettingMobileShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setMobile("Test");
        assertEquals("Test", aftersalePo.getMobile());
    }

    @Test
    void settingAndGettingAddressShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setAddress("Test");
        assertEquals("Test", aftersalePo.getAddress());
    }

    @Test
    void settingAndGettingStatusShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setStatus(1);
        assertEquals(1, aftersalePo.getStatus());
    }

    @Test
    void settingAndGettingInArbitrationShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setIn_arbitration(1);
        assertEquals(1, aftersalePo.getIn_arbitration());
    }

    @Test
    void settingAndGettingQuantityShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setQuantity(10);
        assertEquals(10, aftersalePo.getQuantity());
    }

    @Test
    void settingAndGettingGmtApplyShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        LocalDateTime now = LocalDateTime.now();
        aftersalePo.setGmtApply(now);
        assertEquals(now, aftersalePo.getGmtApply());
    }

    @Test
    void settingAndGettingGmtEndShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        LocalDateTime now = LocalDateTime.now();
        aftersalePo.setGmtEnd(now);
        assertEquals(now, aftersalePo.getGmtEnd());
    }

    @Test
    void settingAndGettingOrderIdShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setOrder_id(1L);
        assertEquals(1L, aftersalePo.getOrder_id());
    }

    @Test
    void settingAndGettingOrderItemIdShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setOrder_item_id(1L);
        assertEquals(1L, aftersalePo.getOrder_item_id());
    }

    @Test
    void settingAndGettingProductItemIdShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setProduct_item_id(1L);
        assertEquals(1L, aftersalePo.getProduct_item_id());
    }

    @Test
    void settingAndGettingProductIdShouldWorkCorrectly() {
        AftersalePo aftersalePo = new AftersalePo();
        aftersalePo.setProduct_id(1L);
        assertEquals(1L, aftersalePo.getProduct_id());
    }
}

