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
public class Aftersale implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Aftersale.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer NEW = 100;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PENDING = 200;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer REFUND = 301;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer EXCHANGE = 302;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer FIX = 303;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer EXCHANGEING = 304;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer CANCLE = 400;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer FINISH = 500;
    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {NEW, "新订单"},
            {PENDING, "待处理"},
            {REFUND, "退款"},
            {EXCHANGE, "换货"},
            {FIX, "维修"},
            {EXCHANGEING, "换货中"},
            {CANCLE, "已取消"},
            {FINISH, "已完成"},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    /**
     * 允许的状态迁移
     */
    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {NEW, Arrays.asList(PENDING, CANCLE)},
            {PENDING, Arrays.asList(REFUND, EXCHANGE, FIX, CANCLE)},
            {REFUND, Arrays.asList(FINISH)},
            {EXCHANGE, Arrays.asList(EXCHANGEING, FINISH)},
            {FIX, Arrays.asList(FINISH)},
            {EXCHANGEING, Arrays.asList(FINISH)},
            {CANCLE, Arrays.asList(FINISH)},
            {FINISH, Arrays.asList()},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public boolean canTransfer(Integer status) {
        return statusTransferMap.get(this.status).contains(status);
    }

    @JsonIgnore
    public String getStatusName() {
        return statusMap.get(this.status);
    }

    private Long id;
    private Integer type;
    private Integer status;
    private String reason;
    private String conclusion;
    private Integer quantity;
    private String contact;
    private String mobile;
    private String address;
    private LocalDateTime gmtApply;
    private LocalDateTime gmtEnd;
    private Long order_item_id;
    private Long product_item_id;
    private Long shop_id;
    private Long arbitration_id;
    private Long customer_id;
    private Long refund_trans_id;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private AftersaleDao aftersaleDao;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
