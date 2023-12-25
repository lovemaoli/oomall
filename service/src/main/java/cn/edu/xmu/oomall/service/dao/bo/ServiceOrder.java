package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
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
@CopyFrom({ServiceOrderPo.class})
public class ServiceOrder implements Serializable{
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer ONSITE= 0;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SEND = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer NEW = 0;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer UNPROCESSED = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PROCESSING = 2;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SUCCESS = 3;
    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {NEW, "待分配"},
            {UNPROCESSED, "未处理"},
            {PROCESSING, "处理中"},
            {SUCCESS, "已处理"},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {NEW, Arrays.asList(UNPROCESSED, PROCESSING)},
            {UNPROCESSED, Arrays.asList(PROCESSING)},
            {PROCESSING, Arrays.asList(NEW, SUCCESS)},
            {SUCCESS, List.of()},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public boolean canTransferTo(Integer status) {
        return statusTransferMap.get(this.status).contains(status);
    }

    public String getStateName() {
        return statusMap.get(this.status);
    }

    private Long id;
    private Integer type;
    private String address;
    private String consignee;
    private String mobile;
    private Integer status;
    private String description;
    private String service_provider_name;
    private String service_provider_mobile;
    private Long shop_id;
    private Long customer_id;
    private Long service_provider_id;
    private Long service_id;
    private Long order_item_id;
    private Long product_id;
    private Long prodcut_item_id;

    private ServiceOrderDao serviceOrderDao;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getService_provider_name() {
        return service_provider_name;
    }

    public void setService_provider_name(String service_provider_name) {
        this.service_provider_name = service_provider_name;
    }

    public String getService_provider_mobile() {
        return service_provider_mobile;
    }

    public void setService_provider_mobile(String service_provider_mobile) {
        this.service_provider_mobile = service_provider_mobile;
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

    public Long getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(Long service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public Long getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(Long order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProdcut_item_id() {
        return prodcut_item_id;
    }

    public void setProdcut_item_id(Long prodcut_item_id) {
        this.prodcut_item_id = prodcut_item_id;
    }

    public ServiceOrderDao getServiceOrderDao() {
        return serviceOrderDao;
    }

    public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public ReturnNo confirm(UserDto user) {
        if(this.status != UNPROCESSED) {
            return ReturnNo.STATENOTALLOW;
        }
        this.setStatus(PROCESSING);
        serviceOrderDao.save(this);
        return ReturnNo.OK;
    }
}
