package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;

import cn.edu.xmu.oomall.service.vo.DraftServiceVo;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.DraftServicePo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({DraftServicePo.class, DraftServiceVo.class})
public class DraftService implements Serializable{
    private Long id;
    private String name;
    private String description;
    private Integer type; // 0:售后 1:售前
    private Integer status; // 0:未审核 1:未通过
    private Long service_provider_id;
    private Long service_id;
    private String category_name;
    private Long region_id;
    private DraftServiceDao draftServiceDao;

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
    public DraftServiceDao getDraftServiceDao() {
        return draftServiceDao;
    }
    public void setDraftServiceDao(DraftServiceDao draftServiceDao) {
        this.draftServiceDao = draftServiceDao;
    }
}
