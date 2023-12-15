//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import cn.edu.xmu.oomall.payment.mapper.generator.ShopChannelPoMapper;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ShopChannelPo;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ShopChannelPoExample;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;
import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * ShopChannel的dao对象
 */
@Repository
public class ShopChannelDao {

    private static final Logger logger = LoggerFactory.getLogger(ShopChannelDao.class);

    private static final String KEY = "SC%d";

    @Value("${oomall.payment.shopchannel.timeout}")
    private long timeout;

    private ShopChannelPoMapper shopChannelPoMapper;

    private RedisUtil redisUtil;

    private ChannelDao channelDao;

    private PayTransDao payTransDao;

    private PayAdaptorFactory factory;

    @Autowired
    @Lazy
    public ShopChannelDao(ShopChannelPoMapper shopChannelPoMapper, RedisUtil redisUtil, ChannelDao channelDao, PayTransDao payTransDao, PayAdaptorFactory factory) {
        this.shopChannelPoMapper = shopChannelPoMapper;
        this.redisUtil = redisUtil;
        this.channelDao = channelDao;
        this.payTransDao = payTransDao;
        this.factory = factory;
    }


    /**
     * 获得bo对象
     *
     * @param po
     * @param redisKey
     * @return
     * @author Ming Qiu
     * <p>
     * date: 2022-11-20 11:46
     */
    private ShopChannel build(ShopChannelPo po, Optional<String> redisKey) {
        ShopChannel ret = CloneFactory.copy(new ShopChannel(), po);
        redisKey.ifPresent(key -> redisUtil.set(key, ret, timeout));
        this.build(ret);
        return ret;
    }

    /**
     * 把bo中设置dao
     *
     * @param bo
     * @author Ming Qiu
     * <p>
     * date: 2022-11-20 11:46
     */
    private ShopChannel build(ShopChannel bo) {
        bo.setShopChannelDao(this);
        bo.setChannelDao(this.channelDao);
        bo.setPayAdaptor(this.factory);
        bo.setShopChannelDao(this);
        bo.setPayTransDao(this.payTransDao);
        return bo;
    }

    /**
     * 由id获得对象
     * @param shopId 商铺id
     * @param id 商铺渠道id
     * @return 商铺渠道对象
     * <p>
     * date: 2022-11-07 0:38
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    /**
     * 2023-dgn1-006
     * @author huangzian
     * 修改bug
     */
    public ShopChannel findById(Long shopId, Long id) {
        ShopChannel ret = null;
        String key = String.format(KEY, id);
        if (redisUtil.hasKey(key)) {
            ret = (ShopChannel) redisUtil.get(key);
            if(!shopId.equals(ret.getShopId()) && !PLATFORM.equals(shopId)){
                throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付渠道", id, shopId));
            }
            this.build(ret);
        } else {
            ShopChannelPo po = shopChannelPoMapper.selectByPrimaryKey(id);
            if (null == po) {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商铺支付渠道", id));
            }
            if(!shopId.equals(po.getShopId()) && !PLATFORM.equals(shopId)){
                throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付渠道", id, shopId));
            }
            ret = this.build(po, Optional.of(key));
        }
        logger.debug("findObjById: id = " + id + " ret = " + ret);
        return ret;
    }

    /**
     * 按照id更新ShopChannel
     *
     * @param shopChannel
     * @return 要删除的key
     * @author Ming Qiu
     * <p>
     * date: 2022-11-10 6:07
     */
    public String save(ShopChannel shopChannel, UserDto user){
        shopChannel.setModifier(user);
        shopChannel.setGmtModified(LocalDateTime.now());
        this.build(shopChannel);
        ShopChannelPo po = CloneFactory.copy(new ShopChannelPo(), shopChannel);
        Integer ret = shopChannelPoMapper.updateByPrimaryKeySelective(po);
        if (0 == ret){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商铺支付渠道", shopChannel.getId()));
        }
        return String.format(KEY, shopChannel.getId());
    }

    /**
     * 获得商铺所有的有效的支付渠道
     *
     * @param shopId   商铺Id
     * @param page     页码
     * @param pageSize 页大小
     */
    public List<ShopChannel> retrieveValidByShopId(Long shopId, Integer page, Integer pageSize) throws RuntimeException {
        if (null == shopId) {
            throw new IllegalArgumentException("ShopChannelDao.retrieveValidByShopId:shopId is null");
        }
        ShopChannelPoExample shopChannelPoExample = new ShopChannelPoExample();
        ShopChannelPoExample.Criteria criteria = shopChannelPoExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andStatusEqualTo(ShopChannel.VALID);
        return retrieveShopChannels(shopChannelPoExample, page, pageSize);
    }

    /**
     * 获得商铺所有的支付渠道
     *
     * @param shopId   商铺Id
     * @param page     页码
     * @param pageSize 页大小
     */
    public List<ShopChannel> retrieveByShopId(Long shopId, Integer page, Integer pageSize) throws RuntimeException {
        if (null == shopId) {
            throw new IllegalArgumentException("ShopChannelDao.retrieveByShopId:shopId is null");
        }
        ShopChannelPoExample shopChannelPoExample = new ShopChannelPoExample();
        ShopChannelPoExample.Criteria criteria = shopChannelPoExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        return retrieveShopChannels(shopChannelPoExample, page, pageSize);
    }

    /**
     * 获得支付渠道的商铺签约的账户
     *
     * @param channelId   支付渠道Id
     * @param page     页码
     * @param pageSize 页大小
     */
    public List<ShopChannel> retrieveByChannelId(Long channelId, Integer page, Integer pageSize) throws RuntimeException {
        if (null == channelId) {
            throw new IllegalArgumentException("ShopChannelDao.retrieveByShopId:shopId is null");
        }
        ShopChannelPoExample shopChannelPoExample = new ShopChannelPoExample();
        ShopChannelPoExample.Criteria criteria = shopChannelPoExample.createCriteria();
        criteria.andChannelIdEqualTo(channelId);
        return retrieveShopChannels(shopChannelPoExample, page, pageSize);
    }

    /**
     * 查询ShopChannel
     *
     * @param shopChannelPoExample 查询条件
     * @param page                 页
     * @param pageSize             分页
     * @return
     */
    private List<ShopChannel> retrieveShopChannels(ShopChannelPoExample shopChannelPoExample, Integer page, Integer pageSize) {
        List<ShopChannel> ret;
        PageHelper.startPage(page, pageSize, false);
        List<ShopChannelPo> shopChannelPoList = shopChannelPoMapper.selectByExample(shopChannelPoExample);
        ret = shopChannelPoList.stream()
                .map(po -> this.build(po, Optional.ofNullable(null)))
                .collect(Collectors.toList());
        return ret;
    }

    /**
     * 插入shopChannel对象
     *
     * @param bo   渠道信息
     * @param user 登录用户
     */
    public ShopChannel insert(ShopChannel bo, UserDto user) {
        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        bo.setId(null);
        this.build(bo);
        ShopChannelPo shopChannelPo = CloneFactory.copy(new ShopChannelPo(), bo);
        try {
            shopChannelPoMapper.insertSelective(shopChannelPo);
        }catch (DataAccessException e){
            Throwable cause = e.getCause();
            if(cause instanceof java.sql.SQLIntegrityConstraintViolationException){
                //错误信息
                String errMsg = ((java.sql.SQLIntegrityConstraintViolationException)cause).getMessage();
                //根据约束名称定位是那个字段
                if(errMsg.indexOf("payment_shop_channel_shop_id_channel_id_uindex") != -1) {
                    throw new BusinessException(ReturnNo.PAY_CHANNEL_EXIST, String.format(ReturnNo.PAY_CHANNEL_EXIST.getMessage(), bo.getShopId(), bo.getChannelId()));
                }
            }
        }
        bo.setId(shopChannelPo.getId());
        return bo;
    }

    /**
     * 删除店铺的账号
     */
    public String delete(Long id) {
        Integer ret = this.shopChannelPoMapper.deleteByPrimaryKey(id);
        if (0 == ret){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商铺支付渠道", id));
        }
        return String.format(ShopChannelDao.KEY, id);
    }


    /**
     * 获得商铺的所有支付渠道
     * @param shopId
     * @return
     */
    public List<ShopChannel> retrieveByShopId(Long shopId) {
        ShopChannelPoExample example = new ShopChannelPoExample();
        ShopChannelPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        return retrieveShopChannels(example, 1, MAX_RETURN);
    }
}
