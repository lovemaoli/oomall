//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.GrouponAct;
import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import cn.edu.xmu.oomall.product.model.Threshold;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({GrouponAct.class, OnSale.class})
public class FullGrouponActDto {

    private Long id;

    private String name;

    private List<Threshold> thresholds;

    private IdNameTypeDto shop;

    private LocalDateTime gmtCreate;
    private IdNameTypeDto creator;
    private LocalDateTime gmtModified;
    private IdNameTypeDto modifier;

    private List<SimpleOnsaleDto> onsaleList;

    public FullGrouponActDto(GrouponAct act){
        super();
        Shop shop = act.getShop();
        List<OnSale> onsales = act.getOnsaleList();
        if (onsales.size() != 1) {
            throw new BusinessException(ReturnNo.INCONSISTENT_DATA);
        }

        CloneFactory.copy(this, act);
        this.setShop1(IdNameTypeDto.builder().id(shop.getId()).name(shop.getName()).build());
        this.setOnsaleList1(onsales.stream().map(o -> CloneFactory.copy(new SimpleOnsaleDto(), o)).collect(Collectors.toList()));
        this.setCreator(IdNameTypeDto.builder().id(act.getCreatorId()).name(act.getCreatorName()).build());
        this.setModifier(IdNameTypeDto.builder().id(act.getModifierId()).name(act.getModifierName()).build());
        this.setThresholds(act.getThresholds());
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

    public List<Threshold> getThresholds() {
        return thresholds;
    }

    public void setThresholds(List<Threshold> thresholds) {
        this.thresholds = thresholds;
    }

    public IdNameTypeDto getShop() {
        return shop;
    }

    public void setShop1(IdNameTypeDto shop) {
        this.shop = shop;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public IdNameTypeDto getCreator() {
        return creator;
    }

    public void setCreator(IdNameTypeDto creator) {
        this.creator = creator;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public IdNameTypeDto getModifier() {
        return modifier;
    }

    public void setModifier(IdNameTypeDto modifier) {
        this.modifier = modifier;
    }

    public List<SimpleOnsaleDto> getOnsaleList() {
        return onsaleList;
    }

    public void setOnsaleList1(List<SimpleOnsaleDto> onsaleList) {
        this.onsaleList = onsaleList;
    }
}
