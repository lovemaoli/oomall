//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.oomall.shop.dao.bo.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import cn.edu.xmu.javaee.core.util.CloneFactory;

import java.time.LocalDateTime;

/**
 */
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({Shop.class})
public class ShopDto {
    private Long id;
    private String name;
    private Long deposit;
    private Long depositThreshold;
    private Byte status;
    private ConsigneeDto consignee;
    private Integer freeThreshold;
    private IdNameTypeDto creator;
    private IdNameTypeDto modifier;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public ShopDto(Shop shop){
        super();
        CloneFactory.copy(this, shop);
        this.creator = IdNameTypeDto.builder().id(shop.getCreatorId()).name(shop.getCreatorName()).build();
        this.modifier = IdNameTypeDto.builder().id(shop.getModifierId()).name(shop.getModifierName()).build();
        this.consignee = ConsigneeDto.builder().regionId(shop.getRegionId()).address(shop.getAddress()).mobile(shop.getMobile()).name(shop.getConsignee()).build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Long getDepositThreshold() {
        return depositThreshold;
    }

    public void setDepositThreshold(Long depositThreshold) {
        this.depositThreshold = depositThreshold;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public ConsigneeDto getConsignee() {
        return consignee;
    }

    public void setConsignee1(ConsigneeDto consignee) {
        this.consignee = consignee;
    }


    public Integer getFreeThreshold() {
        return freeThreshold;
    }

    public void setFreeThreshold(Integer freeThreshold) {
        this.freeThreshold = freeThreshold;
    }

    public IdNameTypeDto getCreator() {
        return creator;
    }

    public void setCreator(IdNameTypeDto creator) {
        this.creator = creator;
    }

    public IdNameTypeDto getModifier() {
        return modifier;
    }

    public void setModifier(IdNameTypeDto modifier) {
        this.modifier = modifier;
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
