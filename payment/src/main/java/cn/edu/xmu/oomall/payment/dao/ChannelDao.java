//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.payment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.payment.dao.bo.Channel;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import cn.edu.xmu.oomall.payment.mapper.generator.ChannelPoMapper;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ChannelPo;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ChannelPoExample;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ChannelDao{

    private static final Logger logger = LoggerFactory.getLogger(ChannelDao.class);

    public static final String KEY = "C%d";

    private RedisUtil redisUtil;

    private ChannelPoMapper channelPoMapper;

    @Lazy
    private ShopChannelDao shopChannelDao;

    private PayAdaptorFactory factory;

    @Autowired
    @Lazy
    public ChannelDao(RedisUtil redisUtil, ChannelPoMapper channelPoMapper, ShopChannelDao shopChannelDao, PayAdaptorFactory factory) {
        this.redisUtil = redisUtil;
        this.channelPoMapper = channelPoMapper;
        this.shopChannelDao = shopChannelDao;
        this.factory = factory;
    }

    private Channel build(ChannelPo po, Optional<String> redisKey){
        Channel channel = CloneFactory.copy(new Channel(), po);
        //永不过期
        redisKey.ifPresent(key ->redisUtil.set(key, channel, -1));
        this.build(channel);
        return channel;
    }

    private  Channel build(Channel bo){
        bo.setShopChannelDao(this.shopChannelDao);
        bo.setChannelDao(this);
        bo.setPayAdaptor(this.factory);
        return bo;
    }

    /**
     * 按照主键获得对象
     * @author Ming Qiu
     * <p>
     * date: 2022-11-20 11:44
     * @param id
     * @return
     * @throws RuntimeException
     */
    public Channel findById(Long id) {
        if (null==id) {
            throw new IllegalArgumentException("ChannelDao.findById: channel id is null");
        }
        logger.debug("findObjById: id = {}",id);
        String key = String.format(KEY, id);
        if (redisUtil.hasKey(key)) {
            Channel channel = (Channel) redisUtil.get(key);
            this.build(channel);
            return channel;
        } else {
            ChannelPo po = this.channelPoMapper.selectByPrimaryKey(id);
            if (null == po) {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "支付渠道", id));
            }
            return this.build(po, Optional.of(key));
        }
    }

    /**
     * 查询所有有效的支付渠道
     * @param page 页码
     * @param pageSize 页大小
     * @return
     */
    public List<Channel> retrieveValid(Integer page, Integer pageSize){
        List<Channel> ret;
        ChannelPoExample channelPoExample = new ChannelPoExample();
        ChannelPoExample.Criteria criteria = channelPoExample.createCriteria();
        criteria.andStatusEqualTo(Channel.VALID);
        PageHelper.startPage(page, pageSize, false);
        List<ChannelPo> validChannels = channelPoMapper.selectByExample(channelPoExample);
        return validChannels.stream().map(po -> CloneFactory.copy(new Channel(), po)).collect(Collectors.toList());
    }

    /**
     * 更新支付渠道的状态
     * @param channel channel对象
     * @param user 操作者
     */
    public String save(Channel channel, UserDto user){
        channel.setModifier(user);
        channel.setGmtModified(LocalDateTime.now());
        ChannelPo po = CloneFactory.copy(new ChannelPo(),channel);
        int ret = channelPoMapper.updateByPrimaryKeySelective(po);
        if(0 == ret){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "支付渠道",po.getId()));
        }
        return String.format(ChannelDao.KEY, po.getId());
    }

}
