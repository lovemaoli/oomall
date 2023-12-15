//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.product.controller.vo.CategoryVo;
import cn.edu.xmu.oomall.product.dao.CategoryDao;
import cn.edu.xmu.oomall.product.mapper.po.CategoryPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ CategoryPo.class, CategoryVo.class })
public class Category extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Category.class);

    @ToString.Exclude
    @JsonIgnore
    public final static Long PARENTID = 0L;

    @ToString.Exclude
    @JsonIgnore
    public final static Long NOCATEGORY = -1L;

    @Builder
    public Category(Long id, Long creatorId, String creatorName, Long modifierId, String modifierName,
            LocalDateTime gmtCreate, LocalDateTime gmtModified, String name, Long pid, Integer commissionRatio) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.name = name;
        this.pid = pid;
        this.commissionRatio = commissionRatio;
    }

    private String name;

    private Long pid;

    private Category parent;

    @Setter
    @JsonIgnore
    private CategoryDao categoryDao;

    @JsonIgnore
    public Category getParent() {
        if (PARENTID.equals(this.id)) {
            return null;
        } else if (null == this.parent && null != this.categoryDao) {
            this.parent = this.categoryDao.findById(this.pid);
        }
        return this.parent;
    }

    private Integer commissionRatio;

    public Integer getCommissionRatio() {
        if (null == this.commissionRatio && null != this.getParent()) {
            Category category = this.getParent();
            this.commissionRatio = this.parent.getCommissionRatio();
        }
        return this.commissionRatio;
    }

    /**
     * 是否为一级分类
     * 
     * @return boolean
     */
    public boolean beFirstClassCategory() {
        if (null == this.pid || PARENTID == this.pid) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得下级分类
     * 
     * @return
     */
    @JsonIgnore
    public List<Category> getChildren() {
        return this.categoryDao.retrieveSubCategory(this.id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setCommissionRatio(Integer commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getName() {
        return name;
    }

    public Long getPid() {
        return pid;
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

    /**
     * 2023-dgn2-004
     * 
     * @author huangzian
     *         使用创建者将职责给了父对象
     */
    public Category createSubCategory(Category category, UserDto creator) {
        if (Category.PARENTID.equals(parent.getPid())) {
            // 一级分类才可以增加子分类
            category.setPid(this.id);
            return this.categoryDao.insert(category, creator);
        } else {
            throw new BusinessException(ReturnNo.CATEGORY_NOTPERMIT,
                    String.format(ReturnNo.CATEGORY_NOTALLOW.getMessage(), id));
        }
    }

    public List<Category> getSubCategoryList(Long id) {
        return this.categoryDao.retrieveSubCategory(id);
    }
}
