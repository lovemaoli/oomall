package cn.edu.xmu.oomall.aftersale.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aftersale_arbitration")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Arbitration.class})
public class ArbitrationPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long arbitration_sn;
    private Integer status;
    private String reason;
    private String shop_reason;
    private String result;
    private String arbitrator;
    private LocalDateTime gmt_arbitration;
    private LocalDateTime gmt_accept;
    private LocalDateTime gmt_apply;
    private LocalDateTime gmt_reply;
    private Long shop_id;
    private Long aftersale_id;
    private Long customer_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArbitration_sn() { return arbitration_sn; }

    public void setArbitration_sn(Long arbitration_sn) { this.arbitration_sn = arbitration_sn; }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getShop_reason() {
        return shop_reason;
    }

    public void setShop_reason(String shop_reason) {
        this.shop_reason = shop_reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getArbitrator() {
        return arbitrator;
    }

    public void setArbitrator(String arbitrator) {
        this.arbitrator = arbitrator;
    }

    public LocalDateTime getGmt_arbitration() {
        return gmt_arbitration;
    }

    public void setGmt_arbitration(LocalDateTime gmt_arbitration) {
        this.gmt_arbitration = gmt_arbitration;
    }

    public LocalDateTime getGmt_accept() {
        return gmt_accept;
    }

    public void setGmt_accept(LocalDateTime gmt_accept) {
        this.gmt_accept = gmt_accept;
    }

    public LocalDateTime getGmt_apply() {
        return gmt_apply;
    }

    public void setGmt_apply(LocalDateTime gmt_apply) {
        this.gmt_apply = gmt_apply;
    }

    public LocalDateTime getGmt_reply() {
        return gmt_reply;
    }

    public void setGmt_reply(LocalDateTime gmt_reply) {
        this.gmt_reply = gmt_reply;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public Long getAftersale_id() {
        return aftersale_id;
    }

    public void setAftersale_id(Long aftersale_id) {
        this.aftersale_id = aftersale_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

}
