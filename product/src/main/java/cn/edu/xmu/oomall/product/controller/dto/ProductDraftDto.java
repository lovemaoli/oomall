//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.dao.bo.ProductDraft;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品草稿对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({ ProductDraft.class })
public class ProductDraftDto {
    private Long id;
    private IdNameTypeDto shop;
    private String name;
    private Long originalPrice;
    private String originPlace;

    private String unit;
    private IdNameTypeDto category;
    private IdNameTypeDto product;
    private IdNameTypeDto creator;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private IdNameTypeDto modifier;

    public ProductDraftDto(ProductDraft draft) {
        super();
        CloneFactory.copy(this, draft);
        this.setCreator(IdNameTypeDto.builder().id(draft.getCreatorId()).name(draft.getCreatorName()).build());
        this.setModifier(IdNameTypeDto.builder().id(draft.getModifierId()).name(draft.getModifierName()).build());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public void setShop(Shop shop) {
        this.shop = IdNameTypeDto.builder().id(shop.getId()).name(shop.getName()).build();
    }

    public IdNameTypeDto getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (category != null)
            this.category = IdNameTypeDto.builder().id(category.getId()).name(category.getName()).build();
    }

    public IdNameTypeDto getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (!product.equals(null)) {
            this.product = IdNameTypeDto.builder().id(product.getId()).name(product.getName()).build();
        }
    }

    public IdNameTypeDto getCreator() {
        return creator;
    }

    public void setCreator(IdNameTypeDto creator) {
        this.creator = creator;
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

    public IdNameTypeDto getModifier() {
        return modifier;
    }

    public void setModifier(IdNameTypeDto modifier) {
        this.modifier = modifier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
