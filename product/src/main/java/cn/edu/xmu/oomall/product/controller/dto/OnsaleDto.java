//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.Activity;
import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({OnSale.class})
public class OnsaleDto {

    private Long id;
    private IdNameTypeDto shop;
    private IdNameTypeDto product;
    private Long price;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Integer maxQuantity;
    private Byte type;
    private Long deposit;
    private LocalDateTime payTime;
    private List<IdNameTypeDto> actList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdNameTypeDto getShop() {
        return shop;
    }

    public IdNameTypeDto getProduct() {
        return product;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<IdNameTypeDto> getActList() {
        return actList;
    }

    public void setShop(Shop shop) {
        this.shop = IdNameTypeDto.builder().id(shop.getId()).name(shop.getName()).build();
    }

    public void setProduct(Product product) {
        this.product = IdNameTypeDto.builder().id(product.getId()).name(product.getName()).build();
    }

    public void setActList(List<Activity> actList) {
        this.actList = actList.stream().map(act -> IdNameTypeDto.builder().id(act.getId()).name(act.getName()).build()).collect(Collectors.toList());
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }
}
