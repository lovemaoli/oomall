//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.dao.ChannelDao;
import cn.edu.xmu.oomall.payment.dao.PayTransDao;
import cn.edu.xmu.oomall.payment.dao.ShopChannelDao;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptor;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostPayTransAdaptorDto;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ShopChannelPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.edu.xmu.oomall.payment.dao.bo.PayTrans.NEW;

/**
 * 商铺支付渠道
 */
@ToString(callSuper = true, doNotUseGetters = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ShopChannelPo.class})
public class ShopChannel extends OOMallObject implements Serializable {
    private static  final Logger logger = LoggerFactory.getLogger(ShopChannel.class);

    /**
     * 有效
     */
    @ToString.Exclude
    @JsonIgnore
    public static Byte VALID = 0;
    /**
     * 无效
     */
    @ToString.Exclude
    @JsonIgnore
    public static Byte INVALID = 1;

    /**
     * 允许的状态迁移
     */
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>(){
        {
            put(VALID, new HashSet<>(){
                {
                    add(INVALID);
                }
            });
            put(INVALID, new HashSet<>(){
                {
                    add(VALID);
                }
            });
        }
    };

    /**
     * 状态和名称的对应
     */
    public static final Map<Byte, String> STATUSNAMES = new HashMap(){
        {
            put(VALID, "有效");
            put(INVALID, "无效");
        }
    };
    /**
     * 是否允许状态迁移
     * @author Ming Qiu
     * <p>
     * date: 2022-11-13 0:25
     * @param status
     * @return
     */
    public boolean allowStatus(Byte status){
        boolean ret = false;

        if (null != status && null != this.status){
            Set<Byte> allowStatusSet = toStatus.get(this.status);
            if (null != allowStatusSet) {
                ret = allowStatusSet.contains(status);
            }
        }
        return ret;
    }
    /**
     * 商铺id
     */
    private Long shopId;

    /**
     * 子商户号
     */
    private String subMchid;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 支付渠道
     */
    @ToString.Exclude
    @JsonIgnore
    private Channel channel;

    private Long channelId;

    @ToString.Exclude
    @JsonIgnore
    @Setter
    private ChannelDao channelDao;

    @ToString.Exclude
    @JsonIgnore
    @Setter
    private ShopChannelDao shopChannelDao;

    @ToString.Exclude
    @JsonIgnore
    private PayAdaptor payAdaptor;

    public void setPayAdaptor(PayAdaptorFactory factory){
        this.payAdaptor = factory.createPayAdaptor(this.getChannel());
    }

    public Channel getChannel() throws BusinessException{
        if (null == this.channel && null != this.channelDao){
            logger.debug("getChannel: this.channelId = {}", this.channelId);
            this.channel = this.channelDao.findById(this.channelId);
        }
        return this.channel;
    }

    /**
     * 解约账户
     */
    public String cancel(){
        //如果该商铺支付渠道的状态是有效的，不允许删除
        if(this.status.equals(ShopChannel.VALID)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "商铺支付渠道", this.id, "有效"));
        }
        if (this.shopChannelDao == null){
            throw new IllegalArgumentException("ShopChannel.cancel: shopChannelDao is null");
        }
        if (this.payAdaptor == null) {
            throw new IllegalArgumentException("ShopChannel.cancel: payAdaptor is null");
        }
        String key =  this.shopChannelDao.delete(this.id);
        this.payAdaptor.cancelChannel(this);

        return key;
    }

    public Byte getStatus() {
        Channel channel = this.getChannel();
        if (Channel.VALID.equals(channel.getStatus())) {
            return this.status;
        }else{
            //支付渠道无效
            return ShopChannel.INVALID;
        }
    }

    /**
     * 有效商户支付渠道
     * @param user
     */
    public String valid(UserDto user){
        return this.changeStatus(VALID, user);
    }

    /**
     * 无效商户支付渠道
     * @param user
     */
    public String invalid(UserDto user){
        return this.changeStatus(INVALID, user);
    }

    /**
     * 修改状态
     * @param status 状态
     * @param user 操作者
     * @return
     */
    private String changeStatus(Byte status, UserDto user){
        if (!this.allowStatus(status)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "商铺", this.id, STATUSNAMES.get(this.status)));
        }
        ShopChannel newShopChannel = new ShopChannel();
        newShopChannel.setStatus(status);
        newShopChannel.setId(id);
        newShopChannel.setChannelId(this.channelId);
        return this.shopChannelDao.save(newShopChannel, user);
    }
    @ToString.Exclude
    @JsonIgnore
    @Setter
    private PayTransDao payTransDao;

    /**
     * 创建支付
     * @param payTrans 支付交易值对象
     * @param user 操作者
     * @return 支付渠道返回值
     * modified by ych
     * task-2023-dgn1-004
     */
    public PostPayTransAdaptorDto createPayment(PayTrans payTrans, UserDto user){
        if (this.status.equals(INVALID)) {
            throw new BusinessException(ReturnNo.PAY_CHANNEL_INVALID, String.format(ReturnNo.PAY_CHANNEL_INVALID.getMessage(), this.getChannel().getName()));
        }
        payTrans.setShopId(this.shopId);
        payTrans.setStatus(NEW);
        PayTrans newObj = this.payTransDao.insert(payTrans, user);
        PayTrans trans = this.payTransDao.findById(this.shopId, newObj.getId());
        logger.debug("createPayment: payAdaptor = {}, newObj = {}", payAdaptor, newObj);

        PostPayTransAdaptorDto adaptorDto = this.payAdaptor.createPayment(trans);
        PayTrans updateTrans = new PayTrans();
        updateTrans.setId(trans.getId());
        if (adaptorDto.getPrepayId() != null) {
            updateTrans.setPrepayId(adaptorDto.getPrepayId());
        }
        this.payTransDao.save(updateTrans, user);

        logger.debug("createPayment: dto = {}", adaptorDto);
        return adaptorDto;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getSubMchid() {
        return subMchid;
    }

    public void setSubMchid(String subMchid) {
        this.subMchid = subMchid;
    }


    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
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
