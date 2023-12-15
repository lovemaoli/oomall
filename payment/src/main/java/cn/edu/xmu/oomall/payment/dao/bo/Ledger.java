//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.dao.*;
import cn.edu.xmu.oomall.payment.mapper.generator.po.LedgerPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 *  台账
 *  存储的数据均为支付渠道获取的数据
 */
@ToString(callSuper = true, doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@CopyFrom({LedgerPo.class})
public class Ledger extends OOMallObject {

    /**
     * 商城内部交易号
     */
    private String outNo;

    /**
     * 支付渠道交易号
     */
    private String transNo;

    /**
     * 金额
     */
    private Long amount;

    /**
     * 对账时间
     */
    private LocalDateTime checkTime;

    /**
     * 台账所属商铺渠道
     */
    @ToString.Exclude
    @JsonIgnore
    private ShopChannel shopChannel;

    private Long shopChannelId;

    @Setter
    @ToString.Exclude
    @JsonIgnore
    private ShopChannelDao shopChannelDao;

    public ShopChannel getShopChannel(){
        if (null == this.shopChannel && null != this.shopChannelDao) {
            this.shopChannel = this.shopChannelDao.findById(PLATFORM, this.shopChannelId);
        }
        return this.shopChannel;
    }

    /**
     * 台账所属渠道, 如果shopChanel为null，此属性应该有值
     */
    @ToString.Exclude
    @JsonIgnore
    private Channel channel;

    private Long channelId;

    @ToString.Exclude
    @JsonIgnore
    @Setter
    private ChannelDao channelDao;

    public Channel getChannel(){
        if (this.channel.equals(null)) {
            if (!this.channelId.equals(null) && !this.channelDao.equals(null)) {
                this.channel = this.channelDao.findById(this.channelId);
            }else{
                this.channel = this.getShopChannel().getChannel();
            }
        }
        return this.channel;
    }


    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }


    public Long getShopChannelId() {
        return shopChannelId;
    }

    public void setShopChannelId(Long shopChannelId) {
        this.shopChannelId = shopChannelId;
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
