package cn.edu.xmu.oomall.product.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuzhicheng
 * @create 2022-12-04 12:19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({Product.class})
public class FullProductDto {
    private Long id;
    private IdNameTypeDto shop;
    private List<IdNameTypeDto> otherProducts;
    private String name;
    private String skuSn;
    private Long originalPrice;
    private Long weight;
    private Byte status;
    private String unit;
    private String barCode;
    private String originPlace;
    private Long freeThreshold;
    private Integer commissionRatio;
    private IdNameTypeDto category;
    private IdNameTypeDto template;
    private IdNameTypeDto creator;
    private IdNameTypeDto modifier;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public FullProductDto(Product product){
        super();
        CloneFactory.copy(this, product);
        //设置返回对象属性
        this.setShop1(IdNameTypeDto.builder().id(product.getShop().getId()).name(product.getShop().getName()).build());
        this.setOtherProducts1(product.getOtherProduct().stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList()));
        this.setCategory1(IdNameTypeDto.builder().id(product.getCategory().getId()).name(product.getCategory().getName()).build());
        this.setTemplate1(IdNameTypeDto.builder().id(product.getTemplate().getId()).name(product.getTemplate().getName()).build());
        this.setCreator(IdNameTypeDto.builder().id(product.getCreatorId()).name(product.getCreatorName()).build());
        this.setModifier(IdNameTypeDto.builder().id(product.getModifierId()).name(product.getModifierName()).build());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdNameTypeDto getShop() {
        return shop;
    }

    public void setShop1(IdNameTypeDto shop) {
        this.shop = shop;
    }

    public List<IdNameTypeDto> getOtherProducts() {
        return otherProducts;
    }

    public void setOtherProducts1(List<IdNameTypeDto> otherProducts) {
        this.otherProducts = otherProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public Long getFreeThreshold() {
        return freeThreshold;
    }

    public void setFreeThreshold(Long freeThreshold) {
        this.freeThreshold = freeThreshold;
    }

    public Integer getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(Integer commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public IdNameTypeDto getCategory() {
        return category;
    }

    public void setCategory1(IdNameTypeDto category) {
        this.category = category;
    }

    public IdNameTypeDto getTemplate() {
        return template;
    }

    public void setTemplate1(IdNameTypeDto template) {
        this.template = template;
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
