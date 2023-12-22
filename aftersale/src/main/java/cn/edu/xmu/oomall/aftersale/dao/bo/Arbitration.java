package cn.edu.xmu.oomall.aftersale.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.AftersaleVo;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({AftersalePo.class, AftersaleVo.class})
public class Arbitration implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Aftersale.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer NEW = 000;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer RESPOND = 100;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer ARBING = 200;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SUCCESS = 300;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer CANCEL = 400;
    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {NEW, "申请中"},
            {RESPOND, "待应诉"},
            {ARBING, "仲裁中"},
            {SUCCESS, "已仲裁"},
            {CANCEL, "取消"},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {NEW, Arrays.asList(RESPOND, CANCEL)},
            {RESPOND, Arrays.asList(ARBING,CANCEL)},
            {ARBING, Arrays.asList(SUCCESS,CANCEL)},
            {SUCCESS, Arrays.asList()},
            {CANCEL, Arrays.asList()},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public boolean canTransfer(Integer status) {
        return statusTransferMap.get(this.status).contains(status);
    }

    @JsonIgnore
    public String getStatusName() {
        return statusMap.get(this.status);
    }
    private Long id;
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

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private AftersaleDao aftersaleDao;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public Long getShop_id() { return shop_id; }
    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }
    public Long getAftersale_id() { return aftersale_id; }
    public void setAftersale_id(Long aftersale_id) {
        this.aftersale_id = aftersale_id;
    }
    public Long getCustomer_id() { return customer_id; }
    public void setCustomer_id(Long customer_id) { this.customer_id = customer_id;}

}
