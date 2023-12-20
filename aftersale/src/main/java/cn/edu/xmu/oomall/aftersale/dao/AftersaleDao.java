package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
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
public class AftersaleDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final static String KEY = "A%d";
    private final AftersalePoMapper aftersalePoMapper;
    private final RedisUtil redisUtil;

    public AftersaleDao(AftersalePoMapper aftersalePoMapper, RedisUtil redisUtil) {
        this.aftersalePoMapper = aftersalePoMapper;
        this.redisUtil = redisUtil;
    }

    public void build(Aftersale bo){
        bo.setAftersaleDao(this);
    }

    public Aftersale build(AftersalePo po, Optional<String> redisKey){
        Aftersale bo = CloneFactory.copy(new Aftersale(), po);
        this.build(bo);
        redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }


    /**
     * 通过id获取售后单
     * @param id 售后单id
     * @return 售后单
     */
    public Aftersale findById(Long id) {
        if(id == null || id <= 0){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format("售后单id不存在"));
        }
        String key = String.format(KEY, id);
        Aftersale bo = (Aftersale) redisUtil.get(key);
        if (bo != null) {
            this.build(bo);
            return bo;
        }
        Optional<AftersalePo> po = aftersalePoMapper.findById(id);
        if (po.isPresent()) {
            bo = build(po.get(), Optional.of(key));
            return bo;
        }
        throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format("售后单id不存在"));
    }
}