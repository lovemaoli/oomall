package cn.edu.xmu.oomall.aftersale.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.AftersaleVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApplyAftersaleVo;
import cn.edu.xmu.oomall.aftersale.dao.*;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
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
@CopyFrom({AftersalePo.class, ApplyAftersaleVo.class})
public class Aftersale implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Aftersale.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer NEW = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PENDING = 2;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PROCESSING = 3;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer CANCEL = 4;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer FINISH = 5;

    @ToString.Exclude
    @JsonIgnore
    public static final Integer RETURN = 0;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer EXCHANGE = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer REPAIR = 2;

    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {NEW, "新售后单"},
            {PENDING, "待售后"},
            {PROCESSING,"售后中"},
            {CANCEL, "已取消"},
            {FINISH, "已完成"},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    /**
     * 允许的状态迁移
     */
    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {NEW, Arrays.asList(PENDING, CANCEL,FINISH)},
            {PENDING, Arrays.asList(PROCESSING, FINISH, CANCEL)},
            {PROCESSING, Arrays.asList(FINISH)},
            {CANCEL, Arrays.asList()},
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
    private Integer type; // 0退货 1换货 2维修
    private String reason;
    private String conclusion;
    private Integer quantity;
    private String contact;
    private String mobile;
    private String address;
    private Integer status;
    private LocalDateTime gmt_apply;
    private LocalDateTime gmt_end;
    private Long order_item_id;
    private Long product_id;
    private Long product_item_id;
    private Long shop_id;
    private Long customer_id;
    private Integer in_arbitration;


    @Setter
    @JsonIgnore
    @ToString.Exclude
    private AftersaleDao aftersaleDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private OrderItemDao orderItemDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private PaymentDao paymentDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ExpressDao expressDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ArbitrationDao arbitrationDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private AftersaleExpressDao aftersaleExpressDao;

    public boolean allowApplyArbitration(UserDto user) {
        return this.status == Aftersale.FINISH && this.customer_id == user.getId() && this.in_arbitration == 0;
    }

    /**
     * 顾客提交仲裁
     * @param reason
     * @param user
     * @return
     */
    public ReturnObject createArbitration(String reason, UserDto user) {
        if (!allowApplyArbitration(user)) {
            logger.error(String.format("售后单%d不允许申请仲裁", this.id));
            return new ReturnObject(ReturnNo.ARBITRATION_STATE_NOTALLOW);
        }
        Arbitration arbitration = new Arbitration();
        arbitration.create(this, reason);
        arbitrationDao.insert(arbitration);
        setIn_arbitration(1);
        aftersaleDao.update(this);
        return new ReturnObject(arbitration);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if (canTransfer(status))
            this.status = status;
        else
            logger.error(String.format("状态不允许从%d迁移到%d", this.status, status));
            throw new BusinessException(ReturnNo.STATENOTALLOW);
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

    public LocalDateTime getGmt_apply() {
        return gmt_apply;
    }

    public void setGmt_apply(LocalDateTime gmt_apply) {
        this.gmt_apply = gmt_apply;
    }

    public LocalDateTime getGmt_end() {
        return gmt_end;
    }

    public void setGmt_end(LocalDateTime gmt_end) {
        this.gmt_end = gmt_end;
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getIn_arbitration() {
        return in_arbitration;
    }

    public void setIn_arbitration(Integer in_arbitration) {
        this.in_arbitration = in_arbitration;
    }

    public AftersaleDao getAftersaleDao() {
        return aftersaleDao;
    }

    public OrderItemDao getOrderItemDao() {
        return orderItemDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public ExpressDao getExpressDao() {
        return expressDao;
    }

    public ArbitrationDao getArbitrationDao() {
        return arbitrationDao;
    }

    public AftersaleExpressDao getAftersaleExpressDao() {
        return aftersaleExpressDao;
    }

    public void save() {
        this.aftersaleDao.save(this);
    }


    public void create(OrderItem orderItem, Aftersale bo, Long user) {
        this.type = bo.getType();
        this.status = Aftersale.NEW;
        this.reason = bo.getReason();
        this.quantity = orderItem.getQuantity();
        this.contact = bo.getContact();
        this.mobile = bo.getMobile();
        this.address = bo.getAddress();
        this.gmt_apply = LocalDateTime.now();
        this.order_item_id = orderItem.getId();
        this.product_item_id = orderItem.getId();
        this.product_id = orderItem.getId(); // TODO
        this.shop_id = orderItem.getShopId();
        this.customer_id = user;
        this.in_arbitration = 0;
    }

    public ReturnNo examine(Long shopid, Boolean confirm){
        logger.error("Aftersale examine");
        return ReturnNo.RESOURCE_FALSIFY;
    }

    public ReturnNo shopAudit(Long shopid){
        logger.error("Aftersale shopAudit");
        return ReturnNo.RESOURCE_FALSIFY;
    }


    public ReturnNo audit(Long shopid, Boolean confirm, String conclusion, Long user) {
        ReturnNo ret;
        if(this.status == Aftersale.NEW) {
            return ReturnNo.AFTERSALE_STATENOTALLOW;
        }
        this.setConclusion(conclusion);
        if(confirm == Boolean.FALSE) {
            this.setStatus(Aftersale.FINISH);
            ret = ReturnNo.OK;
        }else{
            this.setStatus(Aftersale.PENDING);
            ret = this.shopAudit(shopid);
        }
        this.save();
        return ret;
    }
}
