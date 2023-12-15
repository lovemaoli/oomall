//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;

import cn.edu.xmu.oomall.shop.dao.bo.template.Template;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import cn.edu.xmu.oomall.shop.mapper.TemplatePoMapper;

import cn.edu.xmu.oomall.shop.mapper.po.TemplatePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;
import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@Repository
public class TemplateDao {
    private static final Logger logger = LoggerFactory.getLogger(TemplateDao.class);

    private TemplatePoMapper templatePoMapper;

    private RegionTemplateDao regionTemplateDao;

    @Autowired
    @Lazy
    public TemplateDao(TemplatePoMapper templatePoMapper, RegionTemplateDao regionTemplateDao) {
        this.templatePoMapper = templatePoMapper;
        this.regionTemplateDao = regionTemplateDao;
    }

    public Template build(TemplatePo po) {
        Template bo = CloneFactory.copy(new Template(), po);
        bo.setRegionTemplateDao(this.regionTemplateDao);
        return bo;
    }

    /**
     * 查询运费模板
     *
     * @param shopId
     * @param name
     * @param page
     * @param pageSize
     * @throws RuntimeException
     */
    public List<Template> retrieveTemplateByName(Long shopId, String name, Integer page, Integer pageSize) throws RuntimeException {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<TemplatePo> pos = null;
        if(name.trim().isEmpty()) {
            pos = this.templatePoMapper.findByShopId(shopId,pageable);
        }else {
            pos = this.templatePoMapper.findByNameAndShopId(name,shopId,pageable);
        }

        return pos.stream().map(po -> CloneFactory.copy(new Template(),po)).collect(Collectors.toList());
    }

    /**
     * 新增模板
     *
     * @param template
     * @param user
     * @throws RuntimeException
     */
    public Template insert(Template template, UserDto user) throws RuntimeException {
        template.setCreator(user);
        template.setGmtCreate(LocalDateTime.now());


        TemplatePo po = CloneFactory.copy(new TemplatePo(), template);
        logger.debug("insertTemplate: po = {}", po);
        TemplatePo newPo = templatePoMapper.save(po);
        logger.debug("insertTemplate: newPo = {}", newPo);
        template.setId(newPo.getId());
        return template;
    }

    /**
     * 通过id来查找模板
     */
    public Template findById(Long shopId, Long id) {
        logger.debug("findTemplateById: id = {}", id);
        Optional<TemplatePo> ret = this.templatePoMapper.findById(id);
        if (ret.isEmpty()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "运费模板", id));
        } else {
            TemplatePo po = ret.get();
            if (!po.getShopId().equals(shopId) && !shopId.equals(PLATFORM)) {
                throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "模板", po.getId(), shopId));
            }
            return this.build(po);
        }
    }

    /**
     * 修改运费模板
     *
     * @param bo
     * @param user
     * @throws RuntimeException
     */
    public void save(Template bo, UserDto user) throws RuntimeException {
        logger.debug("saveTemplateById: bo ={}, user = {}", bo, user);
        if (bo.getId().equals(null)) {
            throw new IllegalArgumentException("save: template id is null");
        }
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        TemplatePo po = CloneFactory.copy(new TemplatePo(), bo);
        TemplatePo retPo = this.templatePoMapper.save(po);
        if (retPo.getId().equals(IDNOTEXIST)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "运费模板", bo.getId()));
        }
        logger.debug("saveTemplateById: po ={}", po);
    }


}
