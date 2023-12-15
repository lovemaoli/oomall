//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.shop.dao.TemplateDao;
import cn.edu.xmu.oomall.shop.dao.bo.Region;
import cn.edu.xmu.oomall.shop.dao.openfeign.RegionDao;
import cn.edu.xmu.oomall.shop.dao.bo.template.*;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import cn.edu.xmu.oomall.shop.mapper.po.RegionTemplatePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RegionTemplateService {
    private final Logger logger = LoggerFactory.getLogger(RegionTemplateService.class);

    private TemplateDao templateDao;

    private RegionDao regionDao;

    private RedisUtil redisUtil;

    private RegionTemplateDao regionTemplateDao;

    @Autowired
    public RegionTemplateService(TemplateDao templateDao, RegionDao regionDao, RedisUtil redisUtil, RegionTemplateDao regionTemplateDao){
        this.templateDao = templateDao;
        this.regionDao = regionDao;
        this.redisUtil = redisUtil;
        this.regionTemplateDao = regionTemplateDao;
    }
    /**
     * 管理员定义或者修改重量或件数模板明细
     * @param shopId
     * @param bo
     * @param user
     */
    public void saveRegionTemplate(Long shopId,RegionTemplate bo, UserDto user, Class clazz){

        Template template= this.templateDao.findById(shopId, bo.getTemplateId());
        if (!template.gotType().getClass().equals(clazz)){
            throw new BusinessException(ReturnNo.FREIGHT_TEMPLATENOTMATCH);
        }
        String key = template.updateRegion(bo, user);
        this.redisUtil.del(key);
    }

    /**
     * 管理员定义重量或件数模板明细
     * @param shopId
     * @param bo
     * @param user
     */
    public void insertRegionTemplate(Long shopId,RegionTemplate bo, UserDto user, Class clazz){
        logger.debug("insertRegionTemplate: bo={},user={}",bo,user);
        Region region = this.regionDao.findById(bo.getRegionId());
        Template template = this.templateDao.findById(shopId, bo.getTemplateId());

        if (!template.gotType().getClass().equals(clazz)){
            throw new BusinessException(ReturnNo.FREIGHT_TEMPLATENOTMATCH);
        }
        regionTemplateDao.insert(template,bo,user);
    }

    /**
     * 管理员删除地区模板
     * @param shopId
     * @param id  模板id
     * @param rid 地区id
     */

    public void deleteRegionTemplate(Long shopId,Long id,Long rid){
    }

    /**
     * 店家或管理员查询运费模板下属所有地区模板明细
     * @param shopId 商铺id
     * @param templateId 模板id
     * @param page
     * @param pageSize
     */
    public List<RegionTemplate> retrieveRegionTemplateById(Long shopId, Long templateId, Integer page, Integer pageSize){
        Template template= this.templateDao.findById(shopId, templateId);
        return this.regionTemplateDao.retrieveByTemplate(template, page, pageSize);
    }

    /**
     * @ChenLinghui
     * @Task 2023-dgn1-008
     * 克隆模板时，其关联的运费模板也需要克隆
     * @param id
     * @param shopId
     * @param user
     */
    public Template cloneTemplate(Long id, Long shopId, UserDto user) {

        Template template = this.templateDao.findById(shopId,id);

        //克隆template
        template.setId(null);
        template.setDefaultModel((byte) 0);
        Template template1 = templateDao.insert(template,user);

        //按template将所有关联的regionTemplate取出，将其id和objectId设为0重新插入数据库
        List<RegionTemplate> regionTemplates = this.regionTemplateDao.retrieveByTemplate(template);
        regionTemplates.stream().forEach(regionTemplate -> {
            regionTemplate.setTemplateId(template1.getId());
            regionTemplate.setObjectId(null);
            regionTemplateDao.insert(template,regionTemplate,user);
        });
        return template1;

    }
}
