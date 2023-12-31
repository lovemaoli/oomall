package cn.edu.xmu.oomall.aftersale.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "aftersale_order_item")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({OrderItem.class})
public class OrderItemPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long latest_aftersale_id;
    private Integer quantity;
    private Long order_id;
    private Long shop_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLatest_aftersale_id() {
        return latest_aftersale_id;
    }

    public void setLatest_aftersale_id(Long latest_aftersale_id) {
        this.latest_aftersale_id = latest_aftersale_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }
}
