package cn.edu.xmu.oomall.aftersale.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AftersaleVo {
    @NotBlank(message = "售后类型不能为空", groups = {NewGroup.class})
    private Integer type;
    @NotBlank(message = "售后申请理由不能为空", groups = {NewGroup.class})
    private String reason;
    @NotBlank(message = "售后申请结论不能为空", groups = {NewGroup.class})
    private String conclusion;
    @NotBlank(message = "申请数量不能为空", groups = {NewGroup.class})
    private Integer quantity;
    @NotBlank(message = "联系人不能为空", groups = {NewGroup.class})
    private String contact;
    @NotBlank(message = "联系电话不能为空", groups = {NewGroup.class})
    private String mobile;
    @NotBlank(message = "联系地址不能为空", groups = {NewGroup.class})
    private String address;
    @NotBlank(message = "售后状态不能为空", groups = {NewGroup.class})
    private Integer status;
    @NotBlank(message = "申请商品id不能为空", groups = {NewGroup.class})
    private Long order_item_id;
    @NotBlank(message = "申请商品sku id不能为空", groups = {NewGroup.class})
    private Long product_item_id;
    @NotBlank(message = "申请店铺id不能为空", groups = {NewGroup.class})
    private Long shop_id;
    @NotBlank(message = "申请仲裁单id不能为空", groups = {NewGroup.class})
    private Long arbitration_id;
    @NotBlank(message = "申请用户id不能为空", groups = {NewGroup.class})
    private Long customer_id;
    @NotBlank(message = "申请退款单id不能为空", groups = {NewGroup.class})
    private Long refund_trans_id;

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

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(Long order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Long getProduct_item_id() {
        return product_item_id;
    }

    public void setProduct_item_id(Long product_item_id) {
        this.product_item_id = product_item_id;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public Long getArbitration_id() {
        return arbitration_id;
    }

    public void setArbitration_id(Long arbitration_id) {
        this.arbitration_id = arbitration_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getRefund_trans_id() {
        return refund_trans_id;
    }

    public void setRefund_trans_id(Long refund_trans_id) {
        this.refund_trans_id = refund_trans_id;
    }


}
