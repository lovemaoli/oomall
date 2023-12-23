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
public class ArbitrationVo {
    @NotBlank(message = "仲裁理由不能为空", groups = {NewGroup.class})
    private String reason;
    @NotBlank(message = "商品处理理由不能为空", groups = {NewGroup.class})
    private String shop_reason;
    @NotBlank(message = "仲裁结果不能为空", groups = {NewGroup.class})
    private String result;
    @NotBlank(message = "仲裁人不能为空", groups = {NewGroup.class})
    private String arbitrator;
    @NotBlank(message = "仲裁结案时间不能为空", groups = {NewGroup.class})
    private LocalDateTime gmt_arbitration;
    @NotBlank(message = "仲裁接受时间不能为空", groups = {NewGroup.class})
    private LocalDateTime gmt_accept;
    @NotBlank(message = "仲裁申请时间不能为空", groups = {NewGroup.class})
    private LocalDateTime gmt_apply;
    @NotBlank(message = "仲裁回复时间不能为空", groups = {NewGroup.class})
    private LocalDateTime gmt_reply;
    @NotBlank(message = "仲裁店铺id不能为空", groups = {NewGroup.class})
    private Long shop_id;
    @NotBlank(message = "仲裁售后单id不能为空", groups = {NewGroup.class})
    private Long aftersale_id;
    @NotBlank(message = "仲裁用户id不能为空", groups = {NewGroup.class})
    private Long customer_id;

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
