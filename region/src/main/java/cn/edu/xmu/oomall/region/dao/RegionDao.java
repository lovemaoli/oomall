//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.region.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.region.dao.bo.Region;
import cn.edu.xmu.oomall.region.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.region.mapper.po.RegionPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;


@Repository
@RefreshScope
public class RegionDao {
    private final static Logger logger = LoggerFactory.getLogger(RegionDao.class);
    private final static String KEY = "R%d";

    private final static String PARENT_KEY = "RP%d";

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final RegionPoMapper regionPoMapper;
    private final RedisUtil redisUtil;

    @Autowired
    public RegionDao(RegionPoMapper regionPoMapper, RedisUtil redisUtil) {
        this.regionPoMapper = regionPoMapper;
        this.redisUtil = redisUtil;
    }

    public void build(Region bo) {
        bo.setRegionDao(this);
    }

    public Region build(RegionPo po, Optional<String> redisKey) {
        Region bo = CloneFactory.copy(new Region(), po);
        this.build(bo);
        redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }

    /**
     * 通过id查找地区
     *
     * @param id 地区id
     * @return Region
     * @throws RuntimeException
     */
    public Region findById(Long id){
        logger.debug("findById: id = {}", id);
        if(null == id) {
            throw new IllegalArgumentException("id can not be null");
        }
        String key = String.format(KEY, id);
        Region bo = (Region) redisUtil.get(key);
        if (null != bo) {
            logger.debug("findById: hit in redis key = {}, region = {}", key, bo);
            this.build(bo);
            return bo;
        }else {
            Optional<RegionPo> ret = regionPoMapper.findById(id);
            if (ret.isPresent()) {
                logger.debug("findById: retrieve from database region = {}", ret.get());
                return this.build(ret.get(), Optional.of(key));
            } else {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "地区", id));
            }
        }
    }

    /**
     * 通过pid查找子地区
     *
     * @param pid
     * @return List<Region>
     * @throws RuntimeException
     */
    public List<Region> retrieveSubRegionsById(Long pid, Boolean all, Integer page, Integer pageSize) throws RuntimeException {
        logger.debug("retrieveSubRegionsByPid: pid = {}", pid);
        if (null == pid) {
            throw new IllegalArgumentException("pid can not be null.");
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<RegionPo> poPage;
        if (all){
            poPage = this.regionPoMapper.findByPid(pid, pageable);
        }else{
            poPage = this.regionPoMapper.findByPidEqualsAndStatusEquals(pid, Region.VALID, pageable);
        }

        return poPage.stream()
                    .map(po -> this.build(po, Optional.ofNullable(null)))
                    .collect(Collectors.toList());
    }

    /**
     * 创建地区
     *
     * @param bo   地区bo
     * @param user 登录用户
     */
    public Region insert(Region bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        RegionPo po = CloneFactory.copy(new RegionPo(), bo);
        logger.debug("save: po = {}", po);
        po = regionPoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }

    /**
     * 修改地区信息
     *
     * @param bo   地区bo
     * @param user 登录用户
     * @return
     */
    public String save(Region bo, UserDto user) throws RuntimeException {
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        RegionPo po = CloneFactory.copy(new RegionPo(), bo);
        logger.debug("save: po = {}", po);
        RegionPo updatePo = regionPoMapper.save(po);
        if(IDNOTEXIST.equals(updatePo.getId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "地区", bo.getId()));
        }
        return String.format(KEY, bo.getId());
    }

    /**
     * 查找所有上级地区
     * @param region 地区
     * @return 上级地区列表
     * @throws RuntimeException
     */
    public List<Region> retrieveParentsRegions(Region region) {

        String key = String.format(PARENT_KEY, region.getId());
        List<Long> parentIds = (List<Long>) redisUtil.get(key);
        if (null != parentIds){
            return parentIds.stream().map(this::findById).collect(Collectors.toList());
        }else{
            List<Region> regions = new ArrayList<>();
            while(regions.size() < 10 && !Region.ROOT_PID.equals(region.getPid())) {
                region = this.findById(region.getPid());
                regions.add(region);
            }
            this.redisUtil.set(key, (ArrayList<Long>) regions.stream().map(Region::getId).collect(Collectors.toList()), timeout);
            logger.debug("retrieveParentsRegions: regions = {}", regions);
            return regions;
        }
    }
}
