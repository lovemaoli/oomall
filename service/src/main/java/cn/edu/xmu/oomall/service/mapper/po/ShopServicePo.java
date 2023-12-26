package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.bo.ShopService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "service_shop_service")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({ShopService.class})
public class ShopServicePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long shop_id;
    private Long service_id;
    private LocalDateTime create_time;
    private Integer shop_status;
    private Long product_id;
    private Long service_provider_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public LocalDateTime getCreate_time() { return create_time;}

    public void setCreate_time(LocalDateTime create_time) { this.create_time = create_time; }

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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getShop_status() {
        return shop_status;
    }

    public void setShop_status(Integer shop_status) { this.shop_status = shop_status; }
}
