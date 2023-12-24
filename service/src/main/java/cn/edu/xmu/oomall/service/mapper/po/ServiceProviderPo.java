package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.Service;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "service_service_provider")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Service.class})
public class ServiceProviderPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String mobile;
    private Integer service_max_num;
    //    private List<Service> services_list;
    private Integer deposit;
    private Integer deposit_threshold;
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getService_max_num() {
        return service_max_num;
    }

    public void setService_max_num(Integer service_max_num) {
        this.service_max_num = service_max_num;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getDeposit_threshold() {
        return deposit_threshold;
    }

    public void setDeposit_threshold(Integer deposit_threshold) {
        this.deposit_threshold = deposit_threshold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
