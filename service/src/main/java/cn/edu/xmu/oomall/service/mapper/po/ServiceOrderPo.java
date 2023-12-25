package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

@Entity
@Table(name = "service_service_order")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({ServiceOrder.class})
public class ServiceOrderPo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
