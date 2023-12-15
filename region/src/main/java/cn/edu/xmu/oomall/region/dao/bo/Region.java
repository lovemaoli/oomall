//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.region.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.region.controller.vo.RegionVo;
import cn.edu.xmu.oomall.region.dao.RegionDao;
import cn.edu.xmu.oomall.region.mapper.po.RegionPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({RegionPo.class, RegionVo.class})
public class Region extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Region.class);

    /**
     * 两种特殊id
     * 0 -- 最高级地区
     * -1 -- 不存在
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Long TOP_ID = 0L;
    @ToString.Exclude
    @JsonIgnore
    public static final Long ROOT_PID = -1L;

    /**
     * 共三种状态
     */
    //有效
    @ToString.Exclude
    @JsonIgnore
    public static final Byte VALID = 0;
    //停用
    @ToString.Exclude
    @JsonIgnore
    public static final Byte SUSPENDED = 1;
    //废弃
    @ToString.Exclude
    @JsonIgnore
    public static final Byte ABANDONED = 2;

    @ToString.Exclude
    @JsonIgnore
    public static final Map<Byte, String> STATUSNAMES = new HashMap<>() {
        {
            put(VALID, "有效");
            put(SUSPENDED, "停用");
            put(ABANDONED, "废弃");
        }
    };

    /**
     * 允许的状态迁移
     */
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(VALID, new HashSet<>() {
                {
                    add(SUSPENDED);
                    add(ABANDONED);
                }
            });
            put(SUSPENDED, new HashSet<>() {
                {
                    add(VALID);
                    add(ABANDONED);
                }
            });
        }
    };

    /**
     * 是否允许状态迁移
     */
    public boolean allowStatus(Byte status) {
        boolean ret = false;

        if (null != status && null != this.status) {
            Set<Byte> allowStatusSet = toStatus.get(this.status);
            if (null != allowStatusSet) {
                ret = allowStatusSet.contains(status);
            }
        }
        return ret;
    }

    /**
     * 获得当前状态名称
     */
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }


    private Long pid;
    private Byte level;
    private String areaCode;
    private String zipCode;
    private String cityCode;
    private String name;
    private String shortName;
    private String mergerName;
    private String pinyin;
    private Double lng;
    private Double lat;
    private Byte status;

    @JsonIgnore
    @ToString.Exclude
    private Region parentRegion;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private RegionDao regionDao;

    public Region getParentRegion() {
        logger.debug("getParentRegion: pid = {}", this.pid);
        if (!ROOT_PID.equals(this.pid) && null == this.parentRegion && null != this.regionDao) {
            this.parentRegion = this.regionDao.findById(pid);
        }
        return this.parentRegion;
    }

    public Byte getLevel() {
        if (null == this.level) {
            if (TOP_ID.equals(this.pid) || ROOT_PID.equals(this.pid)) {
                this.level = 0;
            } else if (null != this.pid) {
                this.level = (byte) (this.getParentRegion().getLevel() + 1);
            }
        }
        return this.level;
    }

    /**
     * 停用地区
     * @param user 操作者
     * @return 删除的redis key
     */
    public List<String> suspend(UserDto user){
        return this.changeStatus(Region.SUSPENDED, user);
    }

    /**
     * 恢复地区
     * @param user 操作者
     * @return 删除的redis key
     */
    public List<String> resume(UserDto user){
        List<Region> ancestors = this.getAncestors();
        List<Region> invalidRegions = ancestors.stream().filter(o -> !o.getStatus().equals(Region.VALID)).collect(Collectors.toList());
        if (invalidRegions.size() == 0) {
            return this.changeStatus(Region.VALID, user);
        }else{
            throw new BusinessException(ReturnNo.REGION_INVALID,String.format(ReturnNo.REGION_INVALID.getMessage(), invalidRegions.get(0).getId()));
        }
    }

    /**
     * 废弃地区
     * @param user 操作者
     * @return 删除的redis key
     */
    public List<String> abandon(UserDto user){
        return this.changeStatus(Region.ABANDONED, user);
    }
    /**
    *递归修改地区状态
     *
     * @param status     状态
     */
    private List<String> changeStatus(Byte status, UserDto user){
        logger.debug("changeStatus: id = {}, status = {}", this.id, status);

        if (!this.allowStatus(status)) {
            // 状态不允许变动
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "地区", this.id, STATUSNAMES.get(this.status)));
        }
        Region region = new Region();
        region.setStatus(status);
        region.setId(this.id);
        String key = this.regionDao.save(region, user);

        List<Region> subRegions = this.getAllSubRegions(1, MAX_RETURN);
        logger.debug("changeStatus: subRegion = {}", subRegions);
        List<String> keys = subRegions.stream()
                .flatMap(subRegion ->{
                    List sub = null;
                    try {
                        sub = subRegion.changeStatus(status, user);
                    }catch (BusinessException e){
                        if (e.getErrno().equals(ReturnNo.STATENOTALLOW)){
                            sub=new ArrayList();
                        }
                    }
                    return (Stream<String>) sub.stream();
                }).distinct().collect(Collectors.toList());
        keys.add(key);
        logger.debug("changeStatus: keys = {}", keys);
        return keys;
    }

    /**
     * 获得所有的子地区
     * @return 非bo对象
     */
    public List<Region> getAllSubRegions(Integer page, Integer pageSize){
        return this.regionDao.retrieveSubRegionsById(id, true,page, pageSize);
    }

    /**
     * 获得有效的子地区
     * @return 非bo对象
     */
    public List<Region> getValidSubRegions(Integer page, Integer pageSize){
        return this.regionDao.retrieveSubRegionsById(id, false,page, pageSize);
    }

    /**
     * 获得所有上级地区
     * @return
     */
    public List<Region> getAncestors(){
        return this.regionDao.retrieveParentsRegions(this);
    }

    /**
     * 创建下级地区
     * @param region 下级地区
     * @param user 创建者
     * @return
     */
    public Region createSubRegion(Region region, UserDto user){
        if (VALID == this.status || SUSPENDED == this.status) {
            region.setStatus(this.status);
            region.setLevel((byte) (this.getLevel() + 1));
            region.setPid(this.id);
            logger.debug("createSubRegion: region = {}", region);
            return this.regionDao.insert(region, user);
        }else{
            throw new BusinessException(ReturnNo.REGION_ABANDONE, String.format(ReturnNo.REGION_ABANDONE.getMessage(), this.id));
        }

    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
