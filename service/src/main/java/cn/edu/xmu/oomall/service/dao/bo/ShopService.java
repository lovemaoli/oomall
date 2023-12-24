package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.DocFlavor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@CopyFrom({ShopServicePo.class, ShopServiceVo.class})
public class ShopService implements Serializable{
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer VALID = 100;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SHOPSUSPEND = 201;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATSUSPENDSUSPEND = 202;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATSUSPENDVALID = 203;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SHOPCANCEL = 301;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATCANCEL = 302;

    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {VALID, "有效"},
            {SHOPSUSPEND, "商户暂停"},
            {PLATSUSPENDSUSPEND, "平台暂停商户暂停"},
            {PLATSUSPENDVALID, "平台暂停"},
            {SHOPCANCEL, "商户取消"},
            {PLATCANCEL,"平台取消"}
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {VALID, Arrays.asList(SHOPSUSPEND, PLATSUSPENDVALID)},
            {SHOPSUSPEND, Arrays.asList(VALID,PLATSUSPENDSUSPEND,SHOPCANCEL)},
            {PLATSUSPENDSUSPEND, Arrays.asList(SHOPSUSPEND, PLATCANCEL)},
            {PLATSUSPENDVALID, Arrays.asList(VALID, PLATCANCEL)},
            {SHOPCANCEL,List.of()},
            {PLATCANCEL, List.of()},
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
    private String address;
    private String consignee;
    private String mobile;
    private Long service_sn;
    private String description;
    private String service_provider_name;
    private String service_provider_mobile;
    private Integer status;
    private Long shop_id;
    private Long customer_id;
    private Long service_provider_id;
    private Long service_id;
    private Long order_item_id;
    private Long product_id;
    private Long product_item_id;

    private ShopServiceDao shopServiceDao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getService_sn() {
        return service_sn;
    }

    public void setService_sn(Long service_sn) {
        this.service_sn = service_sn;
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

    public Long getProduct_item_id() {
        return product_item_id;
    }

    public void setProduct_item_id(Long product_item_id) {
        this.product_item_id = product_item_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if(canTransfer(status)){
            this.status = status;
        } else {
          throw new BusinessException(ReturnNo.SHOP_SERVICE_STATE_NOTALLOW);
        }
    }

    public ReturnNo changeStatus(Integer status) {
        if (canTransfer(status)) {
            this.status = status;
            // loop...
            return ReturnNo.OK;
        } else {
            return ReturnNo.SHOP_SERVICE_STATE_NOTALLOW;
        }
    }

    public ShopServiceDao getShopServiceDao() {
        return shopServiceDao;
    }

    public void setShopServiceDao(ShopServiceDao shopServiceDao) {
        this.shopServiceDao = shopServiceDao;
    }
}
