package cn.edu.xmu.oomall.payment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.dao.ChannelDao;
import cn.edu.xmu.oomall.payment.dao.ShopChannelDao;
import cn.edu.xmu.oomall.payment.dao.bo.Channel;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChannelService {
    private static  final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    private ChannelDao channelDao;

    private ShopChannelDao shopChannelDao;

    private RedisUtil redisUtil;

    @Autowired
    public ChannelService(ChannelDao channelDao, ShopChannelDao shopChannelDao, RedisUtil redisUtil){
        this.channelDao = channelDao;
        this.shopChannelDao = shopChannelDao;
        this.redisUtil = redisUtil;
    }

    /**
     * 有效支付渠道
     * @param channelId 支付渠道ID
     * @param user 登录用户
     */
    public void validChannel(Long channelId, UserDto user){
        Channel channel = this.channelDao.findById(channelId);
        String key = channel.valid(user);
        this.redisUtil.del(key);
    }

    /**
     * 无效支付渠道
     * @param channelId 支付渠道ID
     * @param user 登录用户
     */
    public void invalidChannel(Long channelId, UserDto user){
        Channel channel = this.channelDao.findById(channelId);
        String key = channel.invalid(user);
        this.redisUtil.del(key);
    }


    /**
     * 获得有效的支付渠道
     * @param shopId 商铺Id
     * @param page 页码
     * @param pageSize 页大小
     */
    public List<Channel> retrieveValidChannel(Long shopId, Integer page, Integer pageSize){
        List<Channel> ret;
        //0 返回当前有效的平台支付渠道
        //其他 返回商铺支持的支付渠道
        if(PLATFORM == shopId) {
            ret = this.channelDao.retrieveValid(page,pageSize);
        } else {
            //获得所有的shopChannel，再判断shopChannel和Channel的状态是否都有效
            List<ShopChannel> shopChannels = this.shopChannelDao.retrieveValidByShopId(shopId, page, pageSize);
            ret = shopChannels.stream()
                        .map(ShopChannel::getChannel)
                        .collect(Collectors.toList());
        }
        return ret;
    }

    /**
     * 获得商铺的所有支付渠道(有效和无效)
     *
     * @param shopId 商户id
     * @param page 页码
     * @param pageSize 页大小
     * @return
     */
    public List<ShopChannel> retrieveShopChannel(Long shopId, Integer page, Integer pageSize) {
        List<ShopChannel> shopChannelList = this.shopChannelDao.retrieveByShopId(shopId, page, pageSize);
        return shopChannelList;
    }

    /**
     * 签约支付渠道
     * @param id 商户id
     * @param shopChannel 商户渠道对象
     * @param user 登录用户
     * @return
     */
    public ShopChannel createShopChannel(Long id, ShopChannel shopChannel, UserDto user){
        Channel channel = this.channelDao.findById(id);
        return channel.createShopChannel(shopChannel, user);
    }

    /**
     * 查询商铺的某一支付渠道
     * @param shopId 商户id
     * @param id 渠道id
     * @return
     */
    public ShopChannel findShopChannel(Long shopId, Long id){
        return this.shopChannelDao.findById(shopId, id);
    }

    /**
     *解约店铺的账户
     * @param shopId 商铺id
     * @param id 渠道id
     */
    public void cancelShopChannel(Long shopId, Long id){
        //先找到shopChannel，判断是否存在和有效
        ShopChannel shopChannel = this.shopChannelDao.findById(shopId, id);
        String key = shopChannel.cancel();
        this.redisUtil.del(key);
    }

    /**
     * 有效商铺支付渠道
     * @param shopId 商铺id
     * @param id 商铺支付渠道id
     * @param user 操作者
     */
    public void validShopChannel(Long shopId, Long id, UserDto user){
        ShopChannel shopChannel = this.shopChannelDao.findById(shopId, id);
        String key = shopChannel.valid(user);
        this.redisUtil.del(key);
    }

    /**
     * 无效商铺支付渠道
     * @param shopId 商铺id
     * @param id 商铺支付渠道id
     * @param user 操作者
     */
    public void invalidShopChannel(Long shopId, Long id, UserDto user){
        ShopChannel shopChannel = this.shopChannelDao.findById(shopId, id);
        String key = shopChannel.invalid(user);
        this.redisUtil.del(key);
    }
}

