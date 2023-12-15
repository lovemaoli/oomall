//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ShopChannel.class})
public class FullShopChannelDto {
    private Long id;
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
    private SimpleChannelDto channel;

    private IdNameTypeDto creator;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private IdNameTypeDto modifier;

    public FullShopChannelDto(ShopChannel shopChannel){
        super();
        this.channel = CloneFactory.copy(new SimpleChannelDto(), shopChannel.getChannel());
        this.creator = IdNameTypeDto.builder().id(shopChannel.getCreatorId()).name(shopChannel.getCreatorName()).build();
        this.modifier = IdNameTypeDto.builder().id(shopChannel.getModifierId()).name(shopChannel.getModifierName()).build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubMchid() {
        return subMchid;
    }

    public void setSubMchid(String subMchid) {
        this.subMchid = subMchid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public SimpleChannelDto getChannel() {
        return channel;
    }

    public IdNameTypeDto getCreator() {
        return creator;
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

    public IdNameTypeDto getModifier() {
        return modifier;
    }

}
