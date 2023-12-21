package cn.edu.xmu.oomall.aftersale.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "aftersale_aftersale")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Aftersale.class})
public class AftersalePo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer type;
    private String reason;
    private String conclusion;
    private Integer quantity;
    private String contact;
    private String mobile;
    private String address;
    private Integer status;
    private LocalDateTime gmtApply;
    private LocalDateTime gmtEnd;
    private Long order_item_id;
    private Long product_item_id;
    private Long shop_id;
    private Long arbitration_id;
    private Long customer_id;
    private Long refund_trans_id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public LocalDateTime getGmtApply() {
        return gmtApply;
    }

    public void setGmtApply(LocalDateTime gmtApply) {
        this.gmtApply = gmtApply;
    }

    public LocalDateTime getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(LocalDateTime gmtEnd) {
        this.gmtEnd = gmtEnd;
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