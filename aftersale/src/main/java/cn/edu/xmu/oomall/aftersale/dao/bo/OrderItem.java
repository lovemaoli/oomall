package cn.edu.xmu.oomall.aftersale.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
//import cn.edu.xmu.oomall.aftersale.controller.dto.OrderItemDto;
//import cn.edu.xmu.oomall.aftersale.controller.vo.OrderItemVo;
//import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.OrderItemDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
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
@CopyFrom({OrderItemPo.class})
public class OrderItem implements Serializable {
    private Long id;
    private Long latest_aftersale_id;
    private Integer quantity;
    private Long order_id;
    private Long shop_id;

    private OrderItemDao orderItemDao;

    private AftersaleDao aftersaleDao;

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

    public Long getOrderId() {
        return order_id;
    }

    public void setOrderId(Long order_id) {
        this.order_id = order_id;
    }

    public Long getShopId() {
        return shop_id;
    }

    public void setShopId(Long shop_id) {
        this.shop_id = shop_id;
    }


    public OrderItemDao getOrderItemDao() {
        return orderItemDao;
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public AftersaleDao getAftersaleDao() {
        return aftersaleDao;
    }

    public void setAftersaleDao(AftersaleDao aftersaleDao) {
        this.aftersaleDao = aftersaleDao;
    }

    public Aftersale createAftersale(Aftersale bo, UserDto user) {
        Aftersale latestAftersale = aftersaleDao.findById(latest_aftersale_id);
        if(latestAftersale == null ||
                latestAftersale.getStatus() == Aftersale.CANCEL ||
                (latestAftersale.getStatus() == Aftersale.FINISH && latestAftersale.getType() == Aftersale.REPAIR && bo.getType() == Aftersale.REPAIR)) {
            Aftersale aftersale = new Aftersale();
            aftersale.create(this, bo, user);
            aftersaleDao.save(aftersale);
            this.latest_aftersale_id = aftersale.getId();
            return aftersale;
        }
        return null;
    }
}
