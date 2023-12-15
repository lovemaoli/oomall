package cn.edu.xmu.oomall.shop.dao.bo.template;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.shop.controller.vo.TemplateVo;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import cn.edu.xmu.oomall.shop.mapper.po.TemplatePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 运费模板对象
 */
@ToString(callSuper = true, doNotUseGetters = true)
@NoArgsConstructor
@CopyFrom({TemplateVo.class, TemplatePo.class})
public class Template extends OOMallObject implements Serializable, Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(Template.class);
    /**
     * 默认模板
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte DEFAULT = 1;

    @ToString.Exclude
    @JsonIgnore
    public static final Byte COMMON = 0;

    @ToString.Exclude
    @JsonIgnore
    public static final String PIECE = "pieceTemplateDao";

    @ToString.Exclude
    @JsonIgnore
    public static final String WEIGHT = "weightTemplateDao";

    @ToString.Exclude
    @JsonIgnore
    public static Map<String, TemplateType> TYPE = new HashMap<>() {
        {
            put(PIECE, new Piece());
            put(WEIGHT, new Weight());
        }
    };

    /**
     * 商铺id
     */
    private Long shopId;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 1 默认
     */
    private Byte defaultModel;

    /**
     * 模板类名
     */
    protected String templateBean;

    /**
     * 分包策略
     */
    protected String divideStrategy;

    /**
     * 打包算法
     */
    protected String packAlgorithm;


    public TemplateType gotType() {
        assert this.templateBean != null;
        return TYPE.get(this.templateBean);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Template template = (Template) super.clone();
        template.setDefaultModel(COMMON);
        return template;
    }

    @ToString.Exclude
    @JsonIgnore
    @Setter
    private RegionTemplateDao regionTemplateDao;



    /**
     * 更新地区的运费模板
     *
     * @param bo   地区模板值
     * @param user 操作者
     * @return 影响的key
     */
    public String updateRegion(RegionTemplate bo, UserDto user) {
        Optional<RegionTemplate> regionTemplate = this.regionTemplateDao.retrieveByTemplateAndRegionId(this, bo.getRegionId());
        RegionTemplate oldBo = null;
        if (regionTemplate.isPresent()) {
            oldBo = regionTemplate.get();
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        bo.setId(oldBo.getId());
        logger.debug("oldBo = {}", oldBo);
        bo.setObjectId(oldBo.getObjectId());
        return this.regionTemplateDao.save(this, bo, user);
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(Byte defaultModel) {
        this.defaultModel = defaultModel;
    }

    /**
     * @return
     * @author ZhaoDong Wang
     * 2023-dgn1-009
     */
    public String getTemplateBean() {
        return templateBean;
    }

    public void setTemplateBean(String templateBean) {
        this.templateBean = templateBean;
    }

    public String getDivideStrategy() {
        return divideStrategy;
    }

    public void setDivideStrategy(String divideStrategy) {
        this.divideStrategy = divideStrategy;
    }

    public String getPackAlgorithm() {
        return packAlgorithm;
    }

    public void setPackAlgorithm(String packAlgorithm) {
        this.packAlgorithm = packAlgorithm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
