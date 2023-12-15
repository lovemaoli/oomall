package cn.edu.xmu.oomall.shop.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.dao.bo.Shop;
import cn.edu.xmu.oomall.shop.dao.bo.template.Template;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({Template.class})
public class TemplateDto {
    private Long id;
    private Long shopId;
    private String name;
    private Byte defaultModel;
    private String divideStrategy;
    private String packAlgorithm;
    private IdNameTypeDto creator;
    private IdNameTypeDto modifier;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public void setCreator(IdNameTypeDto creator) {
        this.creator = creator;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setModifier(IdNameTypeDto modifier) {
        this.modifier = modifier;
    }

    public TemplateDto() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Byte getDefaultModel() {
        return defaultModel;
    }

    public String getDivideStrategy() {
        return divideStrategy;
    }

    public String getPackAlgorithm() {
        return packAlgorithm;
    }


    public void setDefaultModel(Byte defaultModel) {
        this.defaultModel = defaultModel;
    }

    public void setDivideStrategy(String divideStrategy) {
        this.divideStrategy = divideStrategy;
    }

    public void setPackAlgorithm(String packAlgorithm) {
        this.packAlgorithm = packAlgorithm;
    }

}
