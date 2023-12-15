//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.Activity;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品视图对象
 * @author Ming Qiu
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CopyFrom({Product.class, OnSale.class})
public class ProductDto {

    private Long id;
    private String name;
    private Byte status;
    private String skuSn;
    private Long originalPrice;
    private Long weight;
    private String barcode;
    private String unit;
    private String originPlace;
    private List<SimpleProductDto> otherProduct;
    private Long price;
    private Integer quantity;
    private Integer maxQuantity;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private IdNameTypeDto shop;
    private IdNameTypeDto category;
    private Boolean shareable;
    private Long freeThreshold;
    private List<IdNameTypeDto> actList;


    public ProductDto(Product product) {
        super();
        CloneFactory.copy(this, product);
        CloneFactory.copy(this, product.getValidOnsale());
    }

    public Long getId() {
        return id;
    }

    @CopyFrom.Exclude({OnSale.class})
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public List<SimpleProductDto> getOtherProduct() {
        return otherProduct;
    }

    public void setOtherProduct(List<Product> otherProduct) {
        this.otherProduct = otherProduct.stream().map(o -> new SimpleProductDto(o)).collect(Collectors.toList());
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public IdNameTypeDto getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = IdNameTypeDto.builder().id(shop.getId()).name(shop.getName()).build();
    }

    public IdNameTypeDto getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = IdNameTypeDto.builder().id(category.getId()).name(category.getName()).build();
    }

    public Boolean getShareable() {
        return shareable;
    }

    public void setShareable(Boolean shareable) {
        this.shareable = shareable;
    }

    public Long getFreeThreshold() {
        return freeThreshold;
    }

    public void setFreeThreshold(Long freeThreshold) {
        this.freeThreshold = freeThreshold;
    }

    public List<IdNameTypeDto> getActList() {
        return actList;
    }

    public void setActList(List<Activity> actList) {
        this.actList = actList.stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList());
    }
}
