package cn.edu.xmu.oomall.freight.dao.bo;

import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.freight.mapper.po.Region;
import cn.edu.xmu.oomall.freight.mapper.po.Shop;

import java.time.LocalDateTime;

public class Express extends OOMallObject {

    private Long sendRegionId;

    private Region sendRegion;

    private String sendAddress;

    private String sendMobile;

    private Long receivRegionId;

    private Region receivRegion;

    private String receivAddress;

    private String receivMobile;

    private Byte status;

    private String orderCode;

    private Long shop_id;

    private Shop shop;

    private Long shopLogisticsId;

    private ShopLogistics shopLogistics;

    public Long getSendRegionId() {
        return sendRegionId;
    }

    public void setSendRegionId(Long sendRegionId) {
        this.sendRegionId = sendRegionId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public Long getReceivRegionId() {
        return receivRegionId;
    }

    public void setReceivRegionId(Long receivRegionId) {
        this.receivRegionId = receivRegionId;
    }

    public String getReceivAddress() {
        return receivAddress;
    }

    public void setReceivAddress(String receivAddress) {
        this.receivAddress = receivAddress;
    }

    public String getReceivMobile() {
        return receivMobile;
    }

    public void setReceivMobile(String receivMobile) {
        this.receivMobile = receivMobile;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public Long getShopLogisticsId() {
        return shopLogisticsId;
    }

    public void setShopLogisticsId(Long shopLogisticsId) {
        this.shopLogisticsId = shopLogisticsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
