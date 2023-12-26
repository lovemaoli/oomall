package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RegionPoTest {

    @Test
    void settingAndGettingIdShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setId(1L);
        assertEquals(1L, regionPo.getId());
    }

    @Test
    void settingAndGettingPidShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setPid(1L);
        assertEquals(1L, regionPo.getPid());
    }

    @Test
    void settingAndGettingLevelShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setLevel((byte) 1);
        assertEquals((byte) 1, regionPo.getLevel());
    }

    @Test
    void settingAndGettingAreaCodeShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setAreaCode("12345");
        assertEquals("12345", regionPo.getAreaCode());
    }

    @Test
    void settingAndGettingZipCodeShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setZipCode("12345");
        assertEquals("12345", regionPo.getZipCode());
    }

    @Test
    void settingAndGettingCityCodeShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setCityCode("12345");
        assertEquals("12345", regionPo.getCityCode());
    }

    @Test
    void settingAndGettingNameShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setName("Test");
        assertEquals("Test", regionPo.getName());
    }

    @Test
    void settingAndGettingShortNameShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setShortName("Test");
        assertEquals("Test", regionPo.getShortName());
    }

    @Test
    void settingAndGettingMergerNameShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setMergerName("Test");
        assertEquals("Test", regionPo.getMergerName());
    }

    @Test
    void settingAndGettingPinyinShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setPinyin("Test");
        assertEquals("Test", regionPo.getPinyin());
    }

    @Test
    void settingAndGettingLngShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setLng(1.0);
        assertEquals(1.0, regionPo.getLng());
    }

    @Test
    void settingAndGettingLatShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setLat(1.0);
        assertEquals(1.0, regionPo.getLat());
    }

    @Test
    void settingAndGettingStatusShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setStatus((byte) 1);
        assertEquals((byte) 1, regionPo.getStatus());
    }

    @Test
    void settingAndGettingCreatorIdShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setCreatorId(1L);
        assertEquals(1L, regionPo.getCreatorId());
    }

    @Test
    void settingAndGettingCreatorNameShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setCreatorName("Test");
        assertEquals("Test", regionPo.getCreatorName());
    }

    @Test
    void settingAndGettingModifierIdShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setModifierId(1L);
        assertEquals(1L, regionPo.getModifierId());
    }

    @Test
    void settingAndGettingModifierNameShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        regionPo.setModifierName("Test");
        assertEquals("Test", regionPo.getModifierName());
    }

    @Test
    void settingAndGettingGmtCreateShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        LocalDateTime now = LocalDateTime.now();
        regionPo.setGmtCreate(now);
        assertEquals(now, regionPo.getGmtCreate());
    }

    @Test
    void settingAndGettingGmtModifiedShouldWorkCorrectly() {
        RegionPo regionPo = new RegionPo();
        LocalDateTime now = LocalDateTime.now();
        regionPo.setGmtModified(now);
        assertEquals(now, regionPo.getGmtModified());
    }
}