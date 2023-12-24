package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.Service;
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
@Table(name = "service_draftservice")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Service.class})
public class DraftServicePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer type;
    private Integer status;
    private Long service_provider_id;
    private Long service_id;
    private String category_name;
    private Long region_id;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public Long getRegion_id() {
        return region_id;
    }
    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }
}
