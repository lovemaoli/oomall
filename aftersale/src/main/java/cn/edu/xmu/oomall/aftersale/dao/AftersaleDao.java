package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    public Aftersale build(AftersalePo po){
        Aftersale bo = CloneFactory.copy(new Aftersale(), po);
        return bo;
    }


    public Aftersale update(Aftersale bo) {
        AftersalePo po = CloneFactory.copy(new AftersalePo(), bo);
        aftersalePoMapper.save(po);
        return bo;
    }

    /**
     * 通过id获取售后单
     * @param id 售后单id
     * @return 售后单
     */
    public Aftersale findById(Long id) {
        Optional<AftersalePo> po = aftersalePoMapper.findById(id);
        if (po.isPresent()) {
            return build(po.get());
        } else {
            return null;
        }
    }

    public void save(Aftersale aftersale) {
        AftersalePo po = CloneFactory.copy(new AftersalePo(), aftersale);
        aftersalePoMapper.save(po);
    }

    public Aftersale findByBillCode(Long billcode, Long shopid) {
        Optional<AftersalePo> po = aftersalePoMapper.findPoByBillCode(billcode, shopid);
        if(po.isPresent()) {
            return build(po.get());
        } else {
            return null;
        }
    }


}