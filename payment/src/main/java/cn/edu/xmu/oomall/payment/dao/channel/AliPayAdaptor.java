//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao.channel;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.SnowFlakeIdWorker;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostDivPayAdaptorDto;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostPayTransAdaptorDto;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostRefundAdaptorDto;
import cn.edu.xmu.oomall.payment.mapper.openfeign.AliPayMapper;
import cn.edu.xmu.oomall.payment.mapper.openfeign.alipay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 支付宝支付适配器
 */
@Repository("aliPayChannel")
public class AliPayAdaptor implements PayAdaptor {

    private static final String SUCCESS_CODE = "10000";

    private static final String SUCCESS_SUB_CODE = "ACQ.TRADE_HAS_SUCCESS";

    private final Logger logger = LoggerFactory.getLogger(AliPayAdaptor.class);

    private AliPayMapper aliPayMapper;

    private SnowFlakeIdWorker snowFlakeIdWorker;

    /**
     * 状态对应关系
     */
    public static final Map<String, Byte> StatusMap = new HashMap<>(){
        {
            put("WAIT_BUYER_PAY", PayTrans.NEW);
            put("TRADE_CLOSED", PayTrans.FAIL);
            put("TRADE_SUCCESS", PayTrans.SUCCESS);
            put("TRADE_FINISHED", PayTrans.SUCCESS);
        }
    };

    @Autowired
    public AliPayAdaptor(AliPayMapper aliPayMapper, SnowFlakeIdWorker snowFlakeIdWorker) {
        this.aliPayMapper = aliPayMapper;
        this.snowFlakeIdWorker = snowFlakeIdWorker;
    }

    /**
     * 支付交易
     * https://opendocs.alipay.com/open-v3/09d1et?scene=21&pathHash=f2618d9f
     */
    @Override
    public PostPayTransAdaptorDto createPayment(PayTrans payTrans) {
        ShopChannel shop = payTrans.getShopChannel();
        assert shop != null : "AliPayAdaptor.createPayment: shopChannel is null";
        Channel channel = shop.getChannel();
        assert channel != null : "AliPayAdaptor.createPayment: channel is null";

        /*set param*/
        PostPayParam p = PostPayParam.builder()
                .notify_url(channel.getNotifyUrl())
                .out_trade_no(payTrans.getOutNo()).subject(payTrans.getDescription()).build();
        p.setTotal_amount(payTrans.getAmount());

        String ret = this.aliPayMapper.pay(this.getAuthorization(channel.getSpAppid(),shop.getSubMchid()), p);
        PostPayRetObj retObj = JacksonUtil.toObj(ret, PostPayRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment: param = {}, code = {}, message = {}", p, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝支付接口"));
        } else {
            PostPayTransAdaptorDto dto = PostPayTransAdaptorDto.builder().
                    outTradeNo(retObj.getOut_trade_no()).
                    prepayId(retObj.getTrade_no()).
                    totalAmount(retObj.getTotal_amount()).
                    build();
            return dto;
        }
    }

    /**
     * 查询订单信息，用于下单后未收到支付宝返回信息
     * https://opendocs.alipay.com/open-v3/09d1ex?scene=common&pathHash=06def985
     */
    @Override
    public PayTrans returnOrderByOutNo(ShopChannel shopChannel, String outNo) {

        assert shopChannel != null : "AliPayAdaptor.returnOrderByOutNo: shopChannel is null";
        Channel channel = shopChannel.getChannel();
        assert channel != null : "AliPayAdaptor.returnOrderByOutNo: channel is null";

        GetTransParam p = GetTransParam.builder()
                .authorization(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()))
                .out_trade_no(outNo)
                .build();
        return retrievePayTrans(p);
    }

    /**
     * 在支付渠道查询支付交易
     * @param p 查询参数
     * @return 支付交易
     */
    private PayTrans retrievePayTrans(GetTransParam p) {
        String ret = this.aliPayMapper.retrieveOrder(p);
        GetTransRetObj retObj = JacksonUtil.toObj(ret, GetTransRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("retrievePayTrans: param = {}, code = {}, message = {}", p, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝查询支付"));
        }else {
            PayTrans trans = new PayTrans();
            trans.setOutNo(retObj.getOut_trade_no());
            trans.setTransNo(retObj.getTrade_no());
            trans.setSpOpenid(retObj.getBuyer_logon_id());
            trans.setStatus(StatusMap.get(retObj.getTrade_status()));
            trans.setAmount((long) (retObj.getTotal_amount() * 100));
            return trans;
        }
    }


    /**
     * 查询订单信息
     * https://opendocs.alipay.com/open-v3/09d1ex?scene=common&pathHash=06def985
     *
     */
    @Override
    public PayTrans returnOrderByTransId(ShopChannel shopChannel, String transId) {
        assert shopChannel != null : "AliPayAdaptor.returnOrderByOutNo: shopChannel is null";
        Channel channel = shopChannel.getChannel();
        assert channel != null : "AliPayAdaptor.returnOrderByOutNo: channel is null";

        GetTransParam p = GetTransParam.builder()
                .authorization(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()))
                .trade_no(transId)
                .build();
        return retrievePayTrans(p);
    }

    /**
     * 取消支付
     * https://opendocs.alipay.com/open-v3/09d1ev?scene=common&pathHash=2124a438
     */
    @Override
    public void cancelOrder(PayTrans payTrans) {

        ShopChannel shop = payTrans.getShopChannel();
        assert shop != null : "AliPayAdaptor.cancelOrder: shopChannel is null";
        Channel channel = shop.getChannel();
        assert channel != null : "AliPayAdaptor.cancelOrder: channel is null";
        /*set param*/
        CancelOrderParam p = CancelOrderParam.builder()
                .trade_no(payTrans.getTransNo())
                .build();

        String ret = this.aliPayMapper.cancelOrder(this.getAuthorization(channel.getSpAppid(),shop.getSubMchid()), p);
        CancelOrderRetObj retObj = JacksonUtil.toObj(ret, CancelOrderRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createRefund: param = {}, code = {}, message = {}", p, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝取消支付"));
        }
    }

    /**
     * 退款交易,退款和退分账分开进行
     * https://opendocs.alipay.com/open-v3/09d1eu?scene=common&pathHash=19d27b3b
     */
    @Override
    public PostRefundAdaptorDto createRefund(RefundTrans refundTrans) {

        PayTrans payTrans = refundTrans.getPayTrans();
        assert payTrans != null : "AliPayAdaptor.createRefund：payTrans is null";
        ShopChannel shop = refundTrans.getShopChannel();
        assert shop != null : "AliPayAdaptor.createRefund：shopChannel is null";
        Channel channel = refundTrans.getChannel();
        assert channel != null : "AliPayAdaptor.createRefund：channel is null";

        //退款，不包括退分账 https://opendocs.alipay.com/support/01rfw9
        /*set param*/
        PostRefundParam p = PostRefundParam.builder()
                .authorization(this.getAuthorization(channel.getSpAppid(),shop.getSubMchid()))
                .trade_no(payTrans.getTransNo())
                .out_request_no(refundTrans.getOutNo()) // 退款单号
                .build();
        p.setRefund_amount(refundTrans.getAmount());

        String ret = this.aliPayMapper.refund(p);
        PostRefundRetObj retObj = JacksonUtil.toObj(ret, PostRefundRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createRefund: param = {}, code = {}, message = {}", p, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝退款"));
        } else {
            PostRefundAdaptorDto dto = PostRefundAdaptorDto.builder()
                    .transNo(retObj.getTrade_no())
                    .amount(Long.valueOf((long) (retObj.getRefund_fee() * 100)))
                    .successTime(LocalDateTime.now())
                    .userReceivedAccount(retObj.getBuyer_login_id())
                    .build();
            return dto;
        }
    }

    /**
     * 管理员查询退款信息
     */
    @Override
    public RefundTrans returnRefund(ShopChannel shopChannel, String outNo) {
        Channel channel = shopChannel.getChannel();
        assert channel != null : "AliPayAdaptor.returnRefund:channel is null";
        GetRefundParam param = GetRefundParam.builder()
                .out_trade_no(outNo)
                .build();
        String ret = this.aliPayMapper.getRefund(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()), param);
        GetRefundRetObj retObj = JacksonUtil.toObj(ret, GetRefundRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createRefund: param = {}, code = {}, message = {}", param, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝查询退款"));
        } else {
            RefundTrans trans = new RefundTrans();
            trans.setTransNo(retObj.getTrade_no());
            trans.setOutNo(retObj.getOut_trade_no());
            trans.setAmount((long) (retObj.getRefund_amount() * 100));
            if (retObj.getRefund_status().equals(null)) {
                trans.setStatus(RefundTrans.FAIL);
            }else if (retObj.getRefund_status().equals("REFUND_SUCCESS")){
                trans.setStatus(RefundTrans.SUCCESS);
            }
            trans.setDivAmount((long)(retObj.getRefund_royaltys().get(0).getRefund_amount() * 100));
            trans.setSuccessTime(retObj.getGmt_refund_pay());
            return trans;
        }
    }


    /**
     * 分账交易
     */
    @Override
    public PostDivPayAdaptorDto createDivPay(PayTrans payTrans,  String outNo) {
        ShopChannel shop = payTrans.getShopChannel();
        assert shop != null : "AliPayAdaptor.createDivPay:shopChannel is null";
        Channel channel = shop.getChannel();
        assert channel != null : "AliPayAdaptor.createDivPay:channel is null";
        /*set param*/
        PostDivPayParam param = PostDivPayParam.builder()
                .out_request_no(outNo)
                .trade_no(payTrans.getTransNo())
                .royalty_parameters(new ArrayList<>() {
                    {
                        add(OpenApiRoyaltyDetailInfoPojo.builder()
                                .trans_out(shop.getSubMchid())
                                .trans_in(channel.getSpMchid())
                                .amount((double) payTrans.getDivAmount() / 100.0).build());
                    }
                }).build();

        String ret = this.aliPayMapper.postDivPay(this.getAuthorization(channel.getSpAppid(),shop.getSubMchid()), param);
        PostDivPayRetObj retObj = JacksonUtil.toObj(ret, PostDivPayRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createRefund: param = {}, code = {}, message = {}", param, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝支付分账接口"));
        } else {
            PostDivPayAdaptorDto dto = PostDivPayAdaptorDto.builder()
                    .orderId(retObj.getSettle_no())
                    .transactionId(retObj.getTrade_no())
                    .build();
            /*set ret*/
            return dto;
        }
    }


    /**
     * 分账退款
     * https://opendocs.alipay.com/open-v3/09d1eu?scene=common&pathHash=19d27b3b
     * https://opendocs.alipay.com/support/01rg1b
     * 只退分账，不退款
     * refund_amount（退款金额）设置为0元。
     * refund_royalty_parameters 按照以上参数说明设置退分账信息和金额。
     */
    @Override
    public PostRefundAdaptorDto createDivRefund(DivRefundTrans divRefundTrans) {
        RefundTrans refundTrans = divRefundTrans.getRefundTrans();
        assert refundTrans != null : "AliPayAdaptor.createDivRefund: refundTrans is null";
        PayTrans payTrans = refundTrans.getPayTrans();
        assert payTrans != null : "AliPayAdaptor.createDivRefund:payTrans is null";
        ShopChannel shop = divRefundTrans.getShopChannel();
        assert shop != null : "AliPayAdaptor.createDivRefund:shopChannel is null";
        Channel channel = shop.getChannel();
        assert channel != null : "AliPayAdaptor.createDivRefund:channel is null";

        //仅仅退分账
        PostRefundParam p = PostRefundParam.builder()
                .authorization(this.getAuthorization(channel.getSpAppid(),shop.getSubMchid()))
                .trade_no(payTrans.getTransNo())
                .refund_amount(0.0)
                .refund_royalty_parameters(new ArrayList<>() {
                    {
                        add(RoyaltyDetailInfoPojo.builder().trans_out(channel.getSpMchid()).amount(divRefundTrans.getAmount() / 100.0).build());
                    }
                }).build();

        String ret = this.aliPayMapper.refund(p);
        PostRefundRetObj retObj = JacksonUtil.toObj(ret, PostRefundRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createDivRefund: param = {}, code = {}, message = {}", p, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝分账退款"));
        } else {
            PostRefundAdaptorDto dto = PostRefundAdaptorDto.builder()
                    .transNo(retObj.getTrade_no())
                    .userReceivedAccount(retObj.getBuyer_login_id())
                    .build();
            return dto;
        }
    }
    /**
     * 2023-dgn1-006
     * @author huangzian
     */
    @Override
    public void cancelChannel(ShopChannel shopChannel) {
        Channel channel=shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("AliPayAdaptor.cancelChannel:channel is null");
        }
        CancelDivParam param=CancelDivParam.builder()
                .out_request_no(shopChannel.getId().toString())
                .receiver_list(new ArrayList<>(){
                    {
                        add(RoyaltyEntity.builder().account(channel.getSpMchid()).build());
                    }
                })
                .build();
        String ret=this.aliPayMapper.cancelDiv(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()), param);
        CancelDivRetObj retObj= JacksonUtil.toObj(ret, CancelDivRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createChannel: param = {}, code = {}, message = {}", param, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝删除分账"));
        }
    }

    /**
     * 2023-dgn1-006
     * @author huangzian
     */
    @Override
    public void createChannel(ShopChannel shopChannel) {
        Channel channel=shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("AliPayAdaptor.createChannel:channel is null");
        }
        CreateDivParam param= CreateDivParam.builder()
                .authorization(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()))
                .out_request_no(shopChannel.getId().toString())
                .receiver_list(new ArrayList<>(){
                    {
                        add(RoyaltyEntity.builder().account(channel.getSpMchid()).build());
                    }
                })
                .build();
        String ret=this.aliPayMapper.createDiv(this.getAuthorization(channel.getSpAppid(),shopChannel.getSubMchid()),param);
        CreateDivRetObj retObj= JacksonUtil.toObj(ret, CreateDivRetObj.class);
        if (null == retObj){
            ExceptionRetObj retObj1  = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createChannel: param = {}, code = {}, message = {}", param, retObj1.getCode(), retObj1.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "支付宝创建分账"));
        }
    }

    /**
     * 产生authorization
     *
     * @param appId 开放平台颁发的应用id
     * @parm certSn 收款商家账号
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    private String getAuthorization(String appId, String certSn) {
        //请求发起时间，使用Unix时间戳，精确到毫秒。支付宝会拒绝处理过期10分钟后的请求，请保持商家自身系统的时间准确性。
        String time = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        //随机字符串，每次请求需要保持唯一。支付宝使用字段值用于防重放，
        String nounce = snowFlakeIdWorker.nextId().toString();
        return String.format("app_id=${%s},app_cert_sn=${%s},nonce=${%s},timestamp=${%s}", appId,certSn, nounce, time);
    }
}
