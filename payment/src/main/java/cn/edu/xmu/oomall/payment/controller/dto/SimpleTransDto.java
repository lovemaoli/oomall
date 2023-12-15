//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@CopyFrom({Transaction.class, DivPayTrans.class, DivRefundTrans.class, PayTrans.class})
public class SimpleTransDto {
    private Long id;
    private String outNo;
    private String transNo;
    private Long amount;
    private Byte status;
    private LocalDateTime successTime;
    private SimpleChannelDto chanel;

    private IdNameTypeDto adjustor;

    private LocalDateTime adjustTime;

    private LedgerDto ledger;

    public SimpleTransDto(Transaction trans){
        super();
        CloneFactory.copy(this, trans);
        this.chanel = CloneFactory.copy(new SimpleChannelDto(), trans.getChannel());
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(LocalDateTime successTime) {
        this.successTime = successTime;
    }

    public SimpleChannelDto getChanel() {
        return chanel;
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

    public LedgerDto getLedger() {
        return ledger;
    }
}
