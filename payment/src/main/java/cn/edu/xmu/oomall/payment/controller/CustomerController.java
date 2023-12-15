//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.oomall.payment.controller.dto.SimpleChannelDto;
import cn.edu.xmu.oomall.payment.controller.vo.AlipayNotifyVo;
import cn.edu.xmu.oomall.payment.controller.vo.WepayNotifyVo;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.dao.channel.WePayAdaptor;
import cn.edu.xmu.oomall.payment.service.ChannelService;
import cn.edu.xmu.oomall.payment.service.PaymentService;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.SYSTEM;

@RestController
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private ChannelService channelService;

    private PaymentService paymentService;


    @Autowired
    public CustomerController(ChannelService channelService, PaymentService paymentService) {
        this.channelService = channelService;
        this.paymentService = paymentService;
    }

    @GetMapping("/refund/states")
    public ReturnObject getRefundState() {
        return new ReturnObject(RefundTrans.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, RefundTrans.STATUSNAMES.get(key))).collect(Collectors.toList()));
    }

    @GetMapping("/payments/states")
    public ReturnObject getPaymentState() {
        return new ReturnObject(PayTrans.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, PayTrans.STATUSNAMES.get(key))).collect(Collectors.toList()));
    }

    @GetMapping("/divpay/states")
    public ReturnObject getDivpayState() {
        return new ReturnObject(DivPayTrans.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, PayTrans.STATUSNAMES.get(key))).collect(Collectors.toList()));
    }

    @GetMapping("/divrefund/states")
    public ReturnObject getDivrefundState() {
        return new ReturnObject(DivRefundTrans.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, PayTrans.STATUSNAMES.get(key))).collect(Collectors.toList()));
    }

    /**
     *获得有效的支付渠道
     */
    @GetMapping("/channels")
    public ReturnObject retrieveChannel(@RequestParam Long shopId,
                                        @RequestParam(required = false,defaultValue = "1") Integer page,
                                        @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        List<Channel> channels = this.channelService.retrieveValidChannel(shopId, page, pageSize);
        PageDto<SimpleChannelDto> pageDto = new PageDto<>(channels.stream().map(o -> CloneFactory.copy(new SimpleChannelDto(), o)).collect(Collectors.toList()), page, pageSize);
        return new ReturnObject(pageDto);
    }

    /**
     * https://opendocs.alipay.com/open-v3/05w4ku?pathHash=af025e20
     * @param vo
     * @return
     */

    @PostMapping("/notify/payments/alipay")
    public ReturnObject alipayNotify(@Validated @RequestBody AlipayNotifyVo vo) {
        PayTrans payTrans = new PayTrans();
        String status = vo.getTradeStatus();
        if(status.equals("TRADE_SUCCESS")) {
            payTrans.setStatus(PayTrans.SUCCESS);
        } else if(status.equals("TRADE_CLOSED")) {
            payTrans.setStatus(PayTrans.CANCEL);
        } else {
            return new ReturnObject();
        }
        payTrans.setOutNo(vo.getOutTradeNo());
        payTrans.setTransNo(vo.getTradeNo());
        payTrans.setSuccessTime(vo.getGmtPayment());
        payTrans.setAmount(vo.getReceiptAmount());

        this.paymentService.updatePaymentByOutNo(payTrans, SYSTEM);
        return new ReturnObject();

    }


    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_5.shtml
     * @param vo
     * @return
     * modified By ych
     * * task 2023-dgn1-004
     */
    @PostMapping("/notify/payments/wepay")
    public ReturnObject wepayNotify(@Validated @RequestBody WepayNotifyVo vo) {
        PayTrans payTrans = new PayTrans();
        Byte status = WePayAdaptor.PayStatusMap.get(vo.getResource().getTradeState());
        if (status != null) { //.equal改成!=
            payTrans.setStatus(status);
            payTrans.setOutNo(vo.getResource().getOutTradeNo());
            payTrans.setTransNo(vo.getResource().getTransactionId());
            payTrans.setSuccessTime(vo.getResource().getSuccessTime());
            payTrans.setAmount(vo.getResource().getAmount().getTotal());
            this.paymentService.updatePaymentByOutNo(payTrans, SYSTEM);
        }
        return new ReturnObject();
    }
}
