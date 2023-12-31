//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.controller.dto.*;
import cn.edu.xmu.oomall.payment.controller.vo.ShopChannelVo;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.service.ChannelService;
import cn.edu.xmu.oomall.payment.service.RefundService;
import cn.edu.xmu.oomall.payment.service.PaymentService;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.*;

/**
 * 管理人员的接口
 */
@RestController
@RequestMapping(value = "/shops/{shopId}", produces = "application/json;charset=UTF-8")
public class AdminPaymentController {

    private final Logger logger = LoggerFactory.getLogger(AdminPaymentController.class);

    private PaymentService paymentService;
    private RefundService refundService;
    private ChannelService channelService;

    @Autowired
    public AdminPaymentController(PaymentService paymentService, RefundService refundService, ChannelService channelService) {
        this.paymentService = paymentService;
        this.refundService = refundService;
        this.channelService = channelService;
    }


    /**
     *获得商铺所有的支付渠道
     */
    @GetMapping("/shopchannels")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject retrieveShopChannel(@PathVariable Long shopId,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        List<ShopChannel> shopChannelList = this.channelService.retrieveShopChannel(shopId, page, pageSize);
        PageDto<ShopChannelDto> pageDto = new PageDto<>(shopChannelList.stream().map(o -> CloneFactory.copy(new ShopChannelDto(), o)).collect(Collectors.toList()), page, pageSize);
        return new ReturnObject(pageDto);
    }

    /**
     *签约支付渠道
     */
    @Audit(departName = "shops")
    @PostMapping("/channels/{id}/shopchannels")
    public ReturnObject createShopChannels(@PathVariable("shopId") Long shopId,
                                           @PathVariable("id") Long id,
                                           @Validated @RequestBody ShopChannelVo vo,
                                           @LoginUser UserDto user){
        ShopChannel newShopChannel = new ShopChannel();
        newShopChannel.setSubMchid(vo.getSubMchid());
        newShopChannel.setShopId(shopId);
        ShopChannel shopChannel = this.channelService.createShopChannel(id, newShopChannel, user);
        SimpleShopChannelDto dto = CloneFactory.copy(new SimpleShopChannelDto(), shopChannel);
        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    /**
     * 获得商铺支付渠道
     */
    @GetMapping("/shopchannels/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject retrieveShopChannels(@PathVariable("shopId") Long shopId,
                                             @PathVariable("id") Long id){
         ShopChannel shopChannel = this.channelService.findShopChannel(shopId, id);
        return new ReturnObject(new FullShopChannelDto(shopChannel));
    }

    /**
     *解约店铺的账户
     */
    @DeleteMapping("/shopchannels/{id}")
    public ReturnObject delShopChannel(@PathVariable("shopId") Long shopId,
                                       @PathVariable("id") Long id){
        this.channelService.cancelShopChannel(shopId, id);
        return new ReturnObject();
    }

    /**
     *修改支付渠道为有效
     */
    @Audit(departName = "shops")
    @PutMapping("/shopchannels/{id}/valid")
    public ReturnObject updateShopChannelValid(@PathVariable("shopId") Long shopId,
                                               @PathVariable("id") Long id,
                                               @LoginUser UserDto user) {
        this.channelService.validShopChannel(shopId, id, user);
        return new ReturnObject();
    }

    /**
     *修改支付渠道为无效
     */
    @Audit(departName = "shops")
    @PutMapping("/shopchannels/{id}/invalid")
    public ReturnObject updateShopChannelInvalid(@PathVariable("shopId") Long shopId,
                                                 @PathVariable("id") Long id,
                                                 @LoginUser UserDto user) {
        this.channelService.invalidShopChannel(shopId, id, user);
        return new ReturnObject();
    }

    @Audit(departName = "shops")
    @PutMapping("/channels/{id}/valid")
    public ReturnObject updateChannelValid(@PathVariable("shopId") Long shopId,
                                           @PathVariable("id") Long channelId,
                                           @LoginUser UserDto user) {
        if(!shopId.equals(PLATFORM)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付渠道", channelId, shopId));
        }
        this.channelService.validChannel(channelId, user);
        return new ReturnObject();
    }

    @Audit(departName = "shops")
    @PutMapping("/channels/{id}/invalid")
    public ReturnObject updateChannelInvalid(@PathVariable("shopId") Long shopId,
                                           @PathVariable("id") Long channelId,
                                           @LoginUser UserDto user) {
        if(!shopId.equals(PLATFORM)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付渠道", channelId, shopId));
        }
        this.channelService.invalidChannel(channelId, user);
        return new ReturnObject();
    }

    /**
     * 查询退款单
     */
    @GetMapping("/refunds/{id}")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findRefundById(@PathVariable Long shopId, @PathVariable Long id) {
        RefundTrans trans= this.refundService.findRefundById(shopId, id);
        return new ReturnObject(new RefundTransDto(trans));
    }

    /**
     * 调账
     * @author Rui Li
     * task 2023-dgn1-005
     */
    @PutMapping("/refunds/{id}")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject adjustRefund(@PathVariable Long shopId,
                                     @PathVariable Long id,
                                     @LoginUser UserDto user) {
        // 只有管理员可以调账
        if(!shopId.equals(PLATFORM)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "退款单", id, shopId));
        }
        this.refundService.adjustRefund(shopId, id, user);
        return new ReturnObject();
    }

    @GetMapping("/payments/{id}")
    @Audit(departName = "shops")
    public ReturnObject getPayment(@PathVariable Long shopId, @PathVariable Long id) {
        PayTrans payTrans = this.paymentService.findPayment(shopId, id);
        PayTransDto fullPaymentDto = new PayTransDto(payTrans);
        return new ReturnObject(fullPaymentDto);
    }

    /**
     * 查询退款信息
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    @GetMapping("/channels/{id}/refunds")
    @Audit(departName = "shops")
    public ReturnObject retrieveRefunds(@PathVariable Long shopId,
                                        @PathVariable Long id,
                                        @RequestParam(required = false) String transNo,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                        @RequestParam(required = false) Integer status,
                                        @RequestParam(required = false,defaultValue = "1") Integer page,
                                        @RequestParam(required = false,defaultValue = "10") Integer pageSize){

        List<RefundTrans> trans = this.refundService.retrieveRefunds(shopId, id, transNo, beginTime, endTime, status, page, pageSize);
        PageDto<SimpleRefundDto> dto =new PageDto<>(trans.stream().map(o -> CloneFactory.copy(new SimpleRefundDto(), o)).collect(Collectors.toList()), page, pageSize);

        return new ReturnObject(dto);
    }

    /**
     * modified By ych
     * task 2023-dgn1-004
     */
    @GetMapping("/channels/{id}/payments")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject retrievePayments(@PathVariable Long shopId,
                                         @PathVariable Long id,
                                         @RequestParam(required = false) String transNo,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginTime,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                         @RequestParam(required = false) Integer status,
                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        //删去关于时间的判断 上层会赋值
        List<PayTrans> payTrans = this.paymentService.retrievePayments(shopId, id, transNo, beginTime, endTime, status, page, pageSize);
        PageDto<SimpleTransDto> dto = new PageDto<>(payTrans.stream().map(o ->new SimpleTransDto(o)).collect(Collectors.toList()), page, pageSize);
        return new ReturnObject(dto);
    }

    /**
     * 调账
     * @author ych
     * task 2023-dgn1-004
     */
    @PutMapping("/payments/{id}")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject adjustPayment(@PathVariable Long shopId,
                                      @PathVariable Long id,
                                      @LoginUser UserDto user) {
        // 只有管理员可以调账
        if(!shopId.equals(PLATFORM)){
            return new ReturnObject(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付交易", id, shopId));
        }
        this.paymentService.adjustPayment(shopId, id, user);
        return new ReturnObject();
    }
}
