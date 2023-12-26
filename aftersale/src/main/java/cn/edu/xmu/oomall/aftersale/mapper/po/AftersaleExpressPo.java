package cn.edu.xmu.oomall.aftersale.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.AftersaleExpress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "aftersale_aftersale_express")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({AftersaleExpress.class})
public class AftersaleExpressPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long aftersale_id;
    private Long bill_code;
    private Integer sender;
    private Integer status;
    private Long express_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAftersale_id() {
        return aftersale_id;
    }

    public void setAftersale_id(Long aftersale_id) {
        this.aftersale_id = aftersale_id;
    }

    public Long getBill_code() {
        return bill_code;
    }

    public void setBill_code(Long bill_code) {
        this.bill_code = bill_code;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getExpress_id() {
        return express_id;
    }

    public void setExpress_id(Long express_id) {
        this.express_id = express_id;
    }
}
