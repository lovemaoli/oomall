package cn.edu.xmu.oomall.aftersale.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleExpressDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.AftersaleExpressVo;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleExpressDao;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersaleExpressPo;
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
//@CopyFrom({AftersaleExpressPo.class, AftersaleExpressVo.class})
public class AftersaleExpress implements Serializable{
    private Long id;
    private Long aftersale_id;
    private Long bill_code;
    private Integer sender;
    private Integer status;
    private AftersaleExpressDao aftersaleExpressDao;

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

    public AftersaleExpressDao getAftersaleExpressDao() {
        return aftersaleExpressDao;
    }

    public void setAftersaleExpressDao(AftersaleExpressDao aftersaleExpressDao) {
        this.aftersaleExpressDao = aftersaleExpressDao;
    }
}
