//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.dao.template;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.shop.dao.TemplateDao;
import cn.edu.xmu.oomall.shop.dao.bo.Region;
import cn.edu.xmu.oomall.shop.dao.bo.template.Template;
import cn.edu.xmu.oomall.shop.dao.openfeign.RegionDao;
import cn.edu.xmu.oomall.shop.dao.bo.divide.DivideStrategy;
import cn.edu.xmu.oomall.shop.dao.bo.divide.PackAlgorithm;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import cn.edu.xmu.oomall.shop.mapper.RegionTemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.TemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.po.RegionTemplatePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;
import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;

/**
 * 运费模板的dao
 */
@Repository
public class RegionTemplateDao {
    private static final Logger logger = LoggerFactory.getLogger(RegionTemplateDao.class);

    private static final String KEY = "R%dT%d";

    @Value("${oomall.shop.region-template.timeout}")
    private Long timeout;

    @Value("${oomall.shop.region-template.strategy}")
    private String strategy;

    @Value("${oomall.shop.region-template.algorithm}")
    private String algorithm;

    private RegionTemplatePoMapper regionTemplatePoMapper;

    private TemplatePoMapper templatePoMapper;

    private TemplateDao templateDao;

    private ApplicationContext context;

    private RegionDao regionDao;

    private RedisUtil redisUtil;

    @Autowired
    public RegionTemplateDao(ApplicationContext context,
                             RegionTemplatePoMapper regionTemplatePoMapper,
                             RedisUtil redisUtil,
                             RegionDao regionDao,
                             TemplatePoMapper templatePoMapper,
                             TemplateDao templateDao
    ) {
        this.context = context;
        this.regionTemplatePoMapper = regionTemplatePoMapper;
        this.redisUtil = redisUtil;
        this.regionDao = regionDao;
        this.templatePoMapper = templatePoMapper;
        this.templateDao = templateDao;
    }

    /**
     * 返回Bean对象
     *
     * @param template
     * @return
     * @author Ming Qiu
     * <p>
     * date: 2022-11-22 16:11
     */
    private AbstractTemplateDao findTemplateBean(Template template) {
        return (AbstractTemplateDao) context.getBean(template.getTemplateBean());
    }

    /**
     * 方便测试将build方法的类型改为public
     * 设置RegionTemplate的分包策略和打包属性
     *
     * @param template
     * @param po
     * @param redisKey
     * @return
     * @author ZhaoDong Wang
     * 2023-dgn1-009
     */
    public RegionTemplate build(Template template, RegionTemplatePo po, Optional<String> redisKey) {
        AbstractTemplateDao dao = this.findTemplateBean(template);
        RegionTemplate bo = dao.getRegionTemplate(po);
        this.strategy = template.getDivideStrategy();
        if (null != template.getPackAlgorithm())
            this.algorithm = template.getPackAlgorithm();
        this.build(bo);
        logger.debug("getBo: bo = {}", bo);
        redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }

    public RegionTemplate build(RegionTemplate bo) {
        bo.setRegionDao(this.regionDao);
        bo.setTemplateDao(this.templateDao);
        DivideStrategy divideStrategy;
        PackAlgorithm packAlgorithm;
        try {
            packAlgorithm = (PackAlgorithm) Class.forName(this.algorithm).getDeclaredConstructor().newInstance();
            logger.debug("findById: packAlgorithm = {}", packAlgorithm);

            try {
                divideStrategy = (DivideStrategy) Class.forName(this.strategy).getDeclaredConstructor(PackAlgorithm.class).newInstance(packAlgorithm);
                bo.setStrategy(divideStrategy);
            } catch (Exception e) {
                logger.error("findById: message = {}", e.getMessage());
                throw new BusinessException(ReturnNo.APPLICATION_PARAM_ERR, String.format(ReturnNo.APPLICATION_PARAM_ERR.getMessage(), "oomall.shop.region-template.strategy"));
            }

        } catch (Exception e) {
            logger.error("findById: message = {}", e.getMessage());
            throw new BusinessException(ReturnNo.APPLICATION_PARAM_ERR, String.format(ReturnNo.APPLICATION_PARAM_ERR.getMessage(), "oomall.shop.region-template.algorithm"));
        }
        return bo;
    }

    /**
     * 根据关键字找到运费模板
     *
     * @param id
     * @return
     * @throws RuntimeException
     * @author Ming Qiu
     * <p>
     * date: 2022-11-22 12:22
     */
    public RegionTemplate findById(Template template, Long id) throws RuntimeException {
        String key = String.format(KEY, id);
        if (redisUtil.hasKey(key)) {
            RegionTemplate bo = (RegionTemplate) redisUtil.get(key);
            this.build(bo);
            return bo;
        }
        logger.debug("findById: id = {}", id);

        Optional<RegionTemplatePo> ret = regionTemplatePoMapper.findById(id);
        if (ret.isEmpty()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "运费模板", id));
        } else {
            return build(template, ret.get(), Optional.ofNullable(key));
        }
    }

    /**
     * 根据运费模板id和地区id来查找地区模板信息
     * 如果没有与rid对应的地区模板，则会继续查询rid最近的上级地区模板
     * 用于计算运费
     *
     * @param template 运费模板
     * @param rid      地区id
     * @author Zhanyu Liu
     * @date 2022/12/1 10:06
     */
    public RegionTemplate findByTemplateAndRegionId(Template template, Long rid) {
        Optional<RegionTemplate> ret = this.retrieveByTemplateAndRegionId(template, rid);
        //若没有与rid对应的地区模板，继续查找最近的上级地区模板
        if (ret.isEmpty()) {
            List<Region> pRegions = this.regionDao.retrieveParentRegionsById(rid);
            /*
             * 由近到远查询地区模板,只要找到一个不为空的地区模板就结束查询
             */
            for (Region r : pRegions) {
                ret = this.retrieveByTemplateAndRegionId(template, r.getId());
                if (ret.isPresent()) {
                    break;
                }
            }
        }
        if (ret.isPresent()) {
            RegionTemplate bo = ret.get();
            logger.debug("findByTemplateIdAndRegionId: regionTemplate={}", bo);
            return bo;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
    }

    /**
     * 根据运费模板id和地区id来查找地区模板信息
     * 如果没有与rid对应的地区模板，不会继续查询上级地区模板
     *
     * @param template 运费模板
     * @param rid      地区id
     * @throws RuntimeException
     */
    public Optional<RegionTemplate> retrieveByTemplateAndRegionId(Template template, Long rid) throws RuntimeException {
        String key = String.format(KEY, rid, template.getId());
        //先用rid和tid的redisKey来寻找对应的地区模板id
        RegionTemplate regionTemplate = (RegionTemplate) this.redisUtil.get(key);
        if (null == regionTemplate) {
            Optional<RegionTemplatePo> ret = this.regionTemplatePoMapper.findByTemplateIdAndRegionId(template.getId(), rid);
            if (ret.isPresent()) {
                regionTemplate = this.build(template, ret.get(), Optional.ofNullable(key));
                logger.debug("ret.get = {}", ret.get());

            }
        } else {
            regionTemplate = this.build(regionTemplate);
        }

        logger.debug("retrieveByTemplateIdAndRegionId: regionTemplate={}", regionTemplate);
        return Optional.ofNullable(regionTemplate);
    }

    /**
     * 根据模板id查找所有的地区模板信息
     *
     * @param template 模板
     * @param page
     * @param pageSize
     * @throws RuntimeException
     */
    public List<RegionTemplate> retrieveByTemplate(Template template, Integer page, Integer pageSize) {
        List<RegionTemplatePo> ret = null;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        logger.debug("retrieveByTemplateId:page={},pageSize={}", pageable.getPageNumber(), pageable.getPageSize());
        ret = this.regionTemplatePoMapper.findByTemplateId(template.getId(), pageable);

        logger.debug("retrieveRegionTemplateById: po = {}", ret);
        List<RegionTemplate> templateList = ret.stream()
                .map(po -> this.build(template, po, Optional.ofNullable(null)))
                .collect(Collectors.toList());
        return templateList;
    }

    public List<RegionTemplate> retrieveByTemplate(Template template) {
        List<RegionTemplatePo> ret = null;


        ret = this.regionTemplatePoMapper.findByTemplateId(template.getId());
        logger.debug("retrieveRegionTemplateById: po = {}", ret);
        List<RegionTemplate> templateList = ret.stream()
                .map(po -> this.build(template, po, Optional.ofNullable(null)))
                .collect(Collectors.toList());
        return templateList;
    }

    /**
     * 修改模板
     *
     * @param bo
     * @param user
     * @throws RuntimeException
     * @author Ming Qiu
     * <p>
     * date: 2022-11-22 17:14
     */
    public String save(Template template, RegionTemplate bo, UserDto user) throws RuntimeException {
        logger.debug("save: bo ={}, user = {}", bo, user);

        String key = String.format(KEY, bo.getRegionId(), bo.getTemplateId());
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        RegionTemplatePo po = CloneFactory.copy(new RegionTemplatePo(), bo);
        RegionTemplatePo savePo = this.regionTemplatePoMapper.save(po);
        if (savePo.getId().equals(IDNOTEXIST)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "运费模板", bo.getId()));
        }
        AbstractTemplateDao dao = this.findTemplateBean(template);
        dao.save(bo);
        return key;
    }

    /**
     * 删除地区模板
     *
     * @param template
     * @param rid
     * @throws RuntimeException
     */
    public List<String> delRegionByTemplateIdAndRegionId(Template template, Long rid) {
        logger.debug("delRegionByTemplateIdAndRegionId: template ={},rid={}", template, rid);
        List<String> delKeys = new ArrayList<>();
        Optional<RegionTemplatePo> ret = regionTemplatePoMapper.findByTemplateIdAndRegionId(template.getId(), rid);
        logger.debug("delRegionByTemplateIdAndRegionId: ret ={}", ret);
        if (ret.isPresent()) {
            RegionTemplatePo po = ret.get();
            String key = String.format(KEY, po.getId());
            AbstractTemplateDao dao = this.findTemplateBean(template);
            regionTemplatePoMapper.deleteById(po.getId());
            if (redisUtil.hasKey(key))
                redisUtil.del(key);
            delKeys.add(key);
            dao.delete(po.getObjectId());
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        return delKeys;
    }

    /**
     * 删除运费模板，同步删除该模板所拥有的所有地区模板
     *
     * @param template
     * @throws RuntimeException
     */
    public List<String> deleteTemplate(Template template) throws RuntimeException {
        logger.debug("deleteTemplate: template ={}", template);
        List<String> delKeys = new ArrayList<>();
        this.templatePoMapper.deleteById(template.getId());
        AbstractTemplateDao dao = this.findTemplateBean(template);

        Integer page = 0, pageSize = MAX_RETURN;
        while (pageSize.equals(MAX_RETURN)) {
            Pageable pageable = PageRequest.of(page, pageSize);
            List<RegionTemplatePo> ret = regionTemplatePoMapper.findByTemplateId(template.getId(), pageable);
            for (RegionTemplatePo po : ret) {
                String key = String.format(KEY, po.getRegionId(), template.getId());
                delKeys.add(key);
                dao.delete(po.getObjectId());
            }
            page = page + 1;
            pageSize = ret.size();
        }
        return delKeys;
    }

    /**
     * 新增模板
     *
     * @param bo
     * @param user
     * @throws RuntimeException
     * @author Ming Qiu
     * <p>
     * date: 2022-11-22 17:14
     */
    public RegionTemplate insert(Template template, RegionTemplate bo, UserDto user) throws RuntimeException {
        logger.debug("save: bo ={}, user = {}", bo, user);
        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        RegionTemplatePo po = CloneFactory.copy(new RegionTemplatePo(), bo);

        AbstractTemplateDao dao = this.findTemplateBean(template);
        String objectId = dao.insert(bo);
        po.setObjectId(objectId);
        logger.debug("save: po = {}", po);
        try {
            RegionTemplatePo newPo = this.regionTemplatePoMapper.save(po);
            logger.debug("save: newPo = {}", newPo);
            bo.setId(newPo.getId());
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(ReturnNo.FREIGHT_REGIONEXIST, String.format(ReturnNo.FREIGHT_REGIONEXIST.getMessage(), bo.getRegionId()));
        }
        return bo;
    }
}
