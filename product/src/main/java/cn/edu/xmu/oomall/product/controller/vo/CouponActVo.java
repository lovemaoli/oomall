//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.product.model.strategy.BaseCouponDiscount;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 优惠活动vo
 */
@NoArgsConstructor
public class CouponActVo {

    @NotBlank(message = "优惠活动名称不能为空", groups = {NewGroup.class})
    private String name;

    @Min(value = 0, message = "0-不用优惠券")
    private Integer quantity;

    @Min(value = 0, message = "0-每人数量")
    @Max(value = 1, message = "1-总数控制")
    private Integer quantityType;

    @Min(value = 0, message = "0-与活动同")
    private Integer validTerm;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime couponTime;

    @NotBlank(message = "优惠活动规则不能为空", groups = {NewGroup.class})
    private BaseCouponDiscount strategy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }

    public Integer getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(Integer validTerm) {
        this.validTerm = validTerm;
    }

    public LocalDateTime getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(LocalDateTime couponTime) {
        this.couponTime = couponTime;
    }

    public BaseCouponDiscount getStrategy() {
        return strategy;
    }

    public void setStrategy(BaseCouponDiscount strategy) {
        this.strategy = strategy;
    }
}