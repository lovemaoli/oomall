package cn.edu.xmu.oomall.aftersale.dao.bo.arbitration;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.controller.dto.ArbitrationDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
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
@CopyFrom({ArbitrationPo.class, ArbitrationVo.class})
public class Arbitration implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Arbitration.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer NEW = 0;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer RESPOND = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer ARBING = 2;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SUCCESS = 3;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer CANCEL = 4;
    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {NEW, "新订单"},
            {RESPOND, "待商家处理"},
            {ARBING, "仲裁中"},
            {SUCCESS, "仲裁成功"},
            {CANCEL, "仲裁失败"},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {NEW, Arrays.asList(RESPOND, CANCEL)},
            {RESPOND, Arrays.asList(ARBING, CANCEL)},
            {ARBING, Arrays.asList(SUCCESS, CANCEL)},
            {SUCCESS, List.of()},
            {CANCEL, List.of()},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public boolean canTransferTo(Integer status) {
        return statusTransferMap.get(this.status).contains(status);
    }

    public String getStateName() {
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
    private ArbitrationDao arbitrationDao;

    public ArbitrationDto createDto() {
        ArbitrationDto dto = new ArbitrationDto();
        dto.setId(this.id);
        dto.setStatus(this.status);
        dto.setReason(this.reason);
        dto.setShop_reason(this.shop_reason);
        dto.setResult(this.result);
        dto.setArbitrator(this.arbitrator);
        dto.setGmt_arbitration(this.gmt_arbitration);
        dto.setGmt_accept(this.gmt_accept);
        dto.setGmt_apply(this.gmt_apply);
        dto.setGmt_reply(this.gmt_reply);
        dto.setShop_id(this.shop_id);
        dto.setAftersale_id(this.aftersale_id);
        dto.setCustomer_id(this.customer_id);
        return dto;
    }
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
        if (canTransferTo(status))
            this.status = status;
        else
            logger.error(String.format("状态不允许从%d迁移到%d", this.status, status));
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

    public ArbitrationDao getArbitrationDao() {
        return arbitrationDao;
    }

    public void setArbitrationDao(ArbitrationDao arbitrationDao) {
        this.arbitrationDao = arbitrationDao;
    }
}
