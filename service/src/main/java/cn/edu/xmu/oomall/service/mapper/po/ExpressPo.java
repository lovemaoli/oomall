package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
//import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.bo.Express;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "aftersale_express")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Express.class})
public class ExpressPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long service_order_id;
    private Long bill_code;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    public Long getService_order_id() {
        return service_order_id;
    }

    public void setService_order_id(Long service_order_id) {
        this.service_order_id = service_order_id;
    }

    public Long getBill_code() {
        return bill_code;
    }

    public void setBill_code(Long bill_code) {
        this.bill_code = bill_code;
    }
}
