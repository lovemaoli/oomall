//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.payment.controller.dto.RefundTransDto;
import cn.edu.xmu.oomall.payment.controller.vo.PayVo;
import cn.edu.xmu.oomall.payment.controller.vo.RefundVo;
import cn.edu.xmu.oomall.payment.dao.bo.PayTrans;
import cn.edu.xmu.oomall.payment.dao.bo.RefundTrans;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostPayTransAdaptorDto;
import cn.edu.xmu.oomall.payment.service.PaymentService;
import cn.edu.xmu.oomall.payment.service.RefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 内部的接口
 */
@RestController
@RequestMapping(value = "/internal", produces = "application/json;charset=UTF-8")
public class InternalPaymentController {

    private final Logger logger = LoggerFactory.getLogger(InternalPaymentController.class);

    private PaymentService paymentService;
    private RefundService refundService;

    @Autowired
    public InternalPaymentController(PaymentService paymentService, RefundService refundService) {
        this.paymentService = paymentService;
        this.refundService = refundService;
    }

    /**
     * modified by ych
     * task 2023-dgn1-004
     * @param id
     * @param payVo
     * @param user
     * @return
     */
    @PostMapping("/shopchannels/{id}/payments")
    @Audit(departName = "shops")
    public ReturnObject createPayment(@PathVariable Long id,  @Validated @RequestBody PayVo payVo, @LoginUser UserDto user){
        logger.debug("createPayment: orderPayVo = {}", payVo);
        if (payVo.getTimeExpire().isBefore(payVo.getTimeBegin())){
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "结束时间不能早于开始时间");
        }
        if ( payVo.getDivAmount() > payVo.getAmount()){
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "分账金额不能大于支付金额");
        }

        PayTrans payTrans = CloneFactory.copy(new PayTrans(), payVo);

        payTrans.setShopChannelId(id);
        PostPayTransAdaptorDto dto =  this.paymentService.createPayment(payTrans, user);
        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    @PostMapping("/shops/{shopId}/payments/{id}/refunds")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject createRefund(@PathVariable Long shopId, @PathVariable Long id, @Validated @RequestBody(required = true) RefundVo vo, @LoginUser UserDto user){
        logger.debug("createRefund: shopId = {}, id = {}, vo = {}",shopId, id, vo);
        if (vo.getDivAmount() > vo.getAmount()){
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "分账退回金额不能大于退款金额");
        }
        RefundTrans refundTrans = this.refundService.createRefund(shopId, id, vo.getAmount(), vo.getDivAmount(), user);
        RefundTransDto refundTransDto = new RefundTransDto(refundTrans);
        return new ReturnObject(ReturnNo.CREATED, refundTransDto);
    }

    @GetMapping("/shops/{shopId}/refunds/{id}")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findRefundById(@PathVariable Long shopId, @PathVariable Long id) {
        RefundTrans refundTrans = this.refundService.findRefundById(shopId, id);
        return new ReturnObject(new RefundTransDto(refundTrans));
    }

    /**
     * modified by ych
     * task 2023-dgn1-004
     */
    @PutMapping("/channels/{id}/payments/div")
    public ReturnObject divPayment(@PathVariable Long id, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        //删去了 endTime为null的情况 上层会赋值
        this.paymentService.divPayment(id, endTime);
        return new ReturnObject();
    }



    @DeleteMapping("/shops/{shopId}/payments/{id}")
    @Audit(departName = "shops")
    public ReturnObject cancelPayment(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user) {
        this.paymentService.cancelPayment(shopId, id, user);
        return new ReturnObject();
    }
}
