//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.oomall.payment.dao.bo.Channel;
import cn.edu.xmu.oomall.payment.dao.bo.DivRefundTrans;
import cn.edu.xmu.oomall.payment.dao.bo.PayTrans;
import cn.edu.xmu.oomall.payment.dao.bo.RefundTrans;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({RefundTrans.class})
public class RefundTransDto {
    private Long id;
    private String outNo;
    private String transNo;
    private Long amount;
    private Long divAmount;
    private LocalDateTime successTime;
    private SimpleChannelDto channel;
    private Byte status;
    private String userReceivedAccount;
    private IdNameTypeDto creator;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private IdNameTypeDto modifier;

    private SimpleTransDto payTrans;

    private SimpleTransDto divTrans;
    private IdNameTypeDto adjustor;
    private LocalDateTime adjustTime;

    private LedgerDto ledger;

    public RefundTransDto(RefundTrans trans){
        super();
        CloneFactory.copy(this, trans);
        this.creator = IdNameTypeDto.builder().id(trans.getCreatorId()).name(trans.getCreatorName()).build();
        this.modifier = IdNameTypeDto.builder().id(trans.getModifierId()).name(trans.getModifierName()).build();
        this.adjustor = IdNameTypeDto.builder().id(trans.getAdjustId()).name(trans.getAdjustName()).build();
        trans.getLedger().ifPresent(o -> this.ledger = CloneFactory.copy(new LedgerDto(), o));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDivAmount() {
        return divAmount;
    }

    public void setDivAmount(Long divAmount) {
        this.divAmount = divAmount;
    }

    public LocalDateTime getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(LocalDateTime successTime) {
        this.successTime = successTime;
    }

    public SimpleChannelDto getChannel() {
        return channel;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUserReceivedAccount() {
        return userReceivedAccount;
    }

    public void setUserReceivedAccount(String userReceivedAccount) {
        this.userReceivedAccount = userReceivedAccount;
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

    public IdNameTypeDto getAdjustor() {
        return adjustor;
    }


    public LocalDateTime getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(LocalDateTime adjustTime) {
        this.adjustTime = adjustTime;
    }

    public SimpleTransDto getPayTrans() {
        return payTrans;
    }

    public SimpleTransDto getDivTrans() {
        return divTrans;
    }

    public LedgerDto getLedger() {
        return ledger;
    }

    public void setChannel(Channel channel) {
        this.channel = CloneFactory.copy(new SimpleChannelDto(), channel);
    }

    public void setPayTrans(PayTrans payTrans) {
        this.payTrans = CloneFactory.copy(new SimpleTransDto(), payTrans);
    }

    /**
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    public void setDivTrans(DivRefundTrans divTrans) {
        if(null == divTrans) return;
        this.divTrans = CloneFactory.copy(new SimpleTransDto(), divTrans);
    }
}
