//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.activity;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.dao.bo.Activity;
import cn.edu.xmu.oomall.product.dao.bo.CouponAct;
import cn.edu.xmu.oomall.product.mapper.jpa.ActivityOnsalePoMapper;
import cn.edu.xmu.oomall.product.mapper.mongo.CouponActPoMapper;
import cn.edu.xmu.oomall.product.mapper.po.ActivityPo;
import cn.edu.xmu.oomall.product.mapper.po.CouponActPo;
import cn.edu.xmu.oomall.product.model.strategy.BaseCouponDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class CouponActDao implements ActivityInf{

    private Logger logger = LoggerFactory.getLogger(CouponActDao.class);

    private CouponActPoMapper actPoMapper;


    @Autowired
    public CouponActDao(CouponActPoMapper actPoMapper) {
        this.actPoMapper = actPoMapper;
    }

    @Override
    public Activity getActivity(ActivityPo po)  throws RuntimeException {
        logger.debug("getActivity: po = {}",po);
        CouponAct bo = CloneFactory.copy(new CouponAct(), po);
        Optional<CouponActPo> ret = this.actPoMapper.findById(po.getObjectId());
        logger.debug("getActivity: ret = {}",ret);
        ret.ifPresent(couponActPo -> {
            CloneFactory.copy(bo, couponActPo);
            bo.setStrategy(BaseCouponDiscount.getInstance(couponActPo.getStrategy()).orElse(null));
        });
        logger.debug("getActivity: bo = {}",bo);
        return bo;
    }

    @Override
    public String insert(Activity bo) throws RuntimeException{
        CouponActPo po = CloneFactory.copy(new CouponActPo(), bo);
        CouponActPo newPo = this.actPoMapper.insert(po);
        return newPo.getObjectId();
    }

    @Override
    public void save(Activity bo) throws RuntimeException{
        CouponActPo po = CloneFactory.copy(new CouponActPo(), bo);
        this.actPoMapper.save(po);
    }
}
