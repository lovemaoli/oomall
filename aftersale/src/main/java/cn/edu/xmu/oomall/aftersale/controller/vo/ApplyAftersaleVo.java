package cn.edu.xmu.oomall.aftersale.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({Aftersale.class})
public class ApplyAftersaleVo {
    @NotBlank(message = "售后类型不能为空", groups = {NewGroup.class})
    private Integer type;
    @NotBlank(message = "售后原因不能为空", groups = {NewGroup.class})
    private String reason;
    @NotBlank(message = "售后人不能为空", groups = {NewGroup.class})
    private String contact;
    @NotBlank(message = "手机号不能为空", groups = {NewGroup.class})
    private String mobile;
    @NotBlank(message = "地址不能为空", groups = {NewGroup.class})
    private String address;
    @NotBlank(message = "申请商品id不能为空", groups = {NewGroup.class})
    private Long product_id;
    @NotBlank(message = "售后商铺itemid不能为空", groups = {NewGroup.class})
    private Long product_item_id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProduct_item_id() {
        return product_item_id;
    }

    public void setProduct_item_id(Long product_item_id) {
        this.product_item_id = product_item_id;
    }




}
