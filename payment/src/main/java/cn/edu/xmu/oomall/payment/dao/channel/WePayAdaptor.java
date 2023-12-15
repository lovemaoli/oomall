//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao.channel;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.oomall.payment.dao.bo.Channel;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.dao.channel.dto.*;
import cn.edu.xmu.oomall.payment.mapper.openfeign.wepay.*;
import cn.edu.xmu.oomall.payment.mapper.openfeign.WePayMapper;
import cn.edu.xmu.oomall.payment.mapper.openfeign.wepay.GetRefundRetObj;
import cn.edu.xmu.oomall.payment.mapper.openfeign.wepay.GetTransRetObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付适配器
 * @author Ming Qiu
 */
@Repository("wePayChannel")
public class WePayAdaptor implements PayAdaptor {

    private static final Logger logger = LoggerFactory.getLogger(WePayAdaptor.class);

    private WePayMapper wePayMapper;

    /**
     * 支付状态对应关系
     */
    public static final Map<String, Byte> PayStatusMap = new HashMap<>(){
        {
            put("NOTPAY", PayTrans.NEW);
            put("CLOSED", PayTrans.FAIL);
            put("SUCCESS", PayTrans.SUCCESS);
        }
    };

    /**
     * 退款状态对应关系
     */
    public static final Map<String, Byte> RefundStatusMap = new HashMap<>(){
        {
            put("PROCESSING", PayTrans.NEW);
            put("ABNORMAL", PayTrans.FAIL);
            put("SUCCESS", PayTrans.SUCCESS);
            put("CLOSED", PayTrans.FAIL);
        }
    };


    @Autowired
    public WePayAdaptor(WePayMapper wePayMapper) {
        this.wePayMapper = wePayMapper;
    }

    /**
     * 支付交易
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_4.shtml
     * */
    @Override
    public PostPayTransAdaptorDto createPayment(PayTrans payTrans) {
        ShopChannel shop = payTrans.getShopChannel();
        if(shop==null)
        {
            throw new IllegalArgumentException("createPayment: shopChannel is null");
        }

        Channel channel = shop.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("createPayment: channel is null");
        }

        /*Set param*/
        PostPayParam param = PostPayParam.builder().
                sp_appid(channel.getSpAppid()).
                sp_mchid(channel.getSpMchid()).
                sub_mchid(shop.getSubMchid()).
                description(payTrans.getDescription()).
                out_trade_no(payTrans.getOutNo()).
                time_expire(payTrans.getTimeExpire()).
                notify_url(channel.getNotifyUrl()).
                amount(PayAmount.builder().total(payTrans.getAmount()).build()).
                payer(Payer.builder().sp_openid(payTrans.getSpOpenid()).build()).
                build();

        String ret= this.wePayMapper.pay(param);
        PostPayRetObj retObj = JacksonUtil.toObj(ret, PostPayRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信支付"));
        } else {
            PostPayTransAdaptorDto dto = PostPayTransAdaptorDto.builder().prepayId(retObj.getPrepayId()).build();
            return dto;
        }
    }

    /**
     * 商户订单号查询
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_2.shtml#part-02
     * */
    @Override
    public PayTrans returnOrderByOutNo(ShopChannel shopChannel, String outNo) {
        Channel channel = shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("returnOrderByOutNo: channel is null");
        }
        String ret = this.wePayMapper.getOrderByOutNo(outNo, channel.getSpMchid(), shopChannel.getSubMchid());
        return this.retrievePayTrans(outNo, ret);
    }

    /**
     * 查询支付交易
     * @param query 查询的值
     * @param ret 查询渠道返回值
     * @return
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    private PayTrans retrievePayTrans(String query, String ret) {
        GetTransRetObj retObj = JacksonUtil.toObj(ret, GetTransRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("retrievePayTrans：query = {}, code = {}, message = {}", query, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信支付查询"));
        }else {
            PayTrans trans = new PayTrans();
            trans.setOutNo(retObj.getOut_trade_no());
            trans.setTransNo(retObj.getTransaction_id());
            trans.setStatus(PayStatusMap.get(retObj.getTrade_state()));
            trans.setSpOpenid(retObj.getTransRetPayer().getSp_openid());
            trans.setAmount(retObj.getTransRetAmount().getTotal());
            return trans;
        }
    }


    /**
     * 商户订单号查询
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_2.shtml#part-02
     * */
    @Override
    public PayTrans returnOrderByTransId(ShopChannel shopChannel, String transId) {
        Channel channel = shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("returnOrderByTransId: channel is null");
        }

        String ret = this.wePayMapper.getOrderByTransId(transId, channel.getSpMchid(), shopChannel.getSubMchid());
        return this.retrievePayTrans(transId, ret);
    }

    /**
     * 取消订单
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_3.shtml
     * */
    @Override
    public void cancelOrder(PayTrans payTrans) {
        ShopChannel shop = payTrans.getShopChannel();
        if(shop==null)
        {
            throw new IllegalArgumentException("cancelOrder: shopChannel is null");
        }
        Channel channel = shop.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("cancelOrder: channel is null");
        }
        /*set param*/
        CancelOrderParam param = CancelOrderParam.builder()
                .sp_mchid(channel.getSpMchid())
                .sub_mchid(shop.getSubMchid())
                .build();

        String ret = this.wePayMapper.cancelOrder(payTrans.getTransNo(), param);
        ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
        if (null != exceptionRetObj) {
            logger.error("createPayment：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信退款"));
        }
    }

    /**
     * 退款交易
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_9.shtml
     * */
    @Override
    public PostRefundAdaptorDto createRefund(RefundTrans refundTrans) {
        ShopChannel shopChannel = refundTrans.getShopChannel();
        if(shopChannel==null)
        {
            throw new IllegalArgumentException("createRefund: shopChannel is null");
        }
        Channel channel = shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("createRefund: channel is null");
        }
        PayTrans payTrans = refundTrans.getPayTrans();
        if(payTrans==null)
        {
            throw new IllegalArgumentException("createRefund: payTrans is null");
        }

        /*set param*/
        PostRefundParam param = PostRefundParam.builder()
                .sub_mchid(shopChannel.getSubMchid())
                .transaction_id(payTrans.getTransNo())
                .out_refund_no(refundTrans.getOutNo())
                .amount(PostRefundAmount.builder()
                                .total(payTrans.getAmount())
                                .refund(refundTrans.getAmount()).build())
                .build();

        String ret = this.wePayMapper.refund(param);
        PostRefundRetObj retObj = JacksonUtil.toObj(ret, PostRefundRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信退款"));
        } else {
            PostRefundAdaptorDto dto = PostRefundAdaptorDto.builder()
                    .transNo(retObj.getRefund_id())
                    .userReceivedAccount(retObj.getUser_received_account())
                    .successTime(retObj.getSuccess_time())
                    .amount(retObj.getAmount().getRefund())
                    .build();
            return dto;
        }
    }

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_10.shtml
     * 管理员查询退款信息
     * modified By Rui Li
     * task 2023-dgn1-005
     * */
    @Override
    public RefundTrans returnRefund(ShopChannel shopChannel, String outNo) {
        String ret = this.wePayMapper.getRefund(outNo, shopChannel.getSubMchid());
        GetRefundRetObj retObj = JacksonUtil.toObj(ret, GetRefundRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment：outNo = {}, code = {}, message = {}", outNo, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信退款查询"));
        } else {
            RefundTrans trans = new RefundTrans();
            trans.setTransNo(retObj.getRefund_id());
            trans.setOutNo(retObj.getOut_refund_no());
            trans.setUserReceivedAccount(retObj.getUser_received_account());
            trans.setStatus(RefundStatusMap.get(retObj.getStatus()));
            trans.setSuccessTime(retObj.getSuccess_time());
            trans.setAmount(retObj.getRefundRetAmount().getPayer_refund());
            trans.setDivAmount(retObj.getRefundRetAmount().getRefundRetFrom().getAmount());
            return trans;
        }
    }


    /**
     * 分账交易
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter8_1_1.shtml
     * */
    @Override
    public PostDivPayAdaptorDto createDivPay(PayTrans payTrans,  String outNo) {
        ShopChannel shop = payTrans.getShopChannel();
        if(shop==null)
        {
            throw new IllegalArgumentException("createDivPay:shopChannel is null");
        }
        Channel channel = shop.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("createDivPay:channel is null");
        }
        /*set param*/
        PostDivPayParam param = PostDivPayParam.builder()
                .appid(channel.getSpAppid())
                .out_order_no(outNo)
                .transaction_id(payTrans.getTransNo())
                .receivers(new ArrayList<>(){
                    {
                        add(Receiver.builder()
                                .account(channel.getSpMchid())
                                .amount(payTrans.getDivAmount())
                                .build());
                    }
                })
                .build();

        String ret = wePayMapper.postDivPay(param);
        PostDivPayRetObj retObj = JacksonUtil.toObj(ret, PostDivPayRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信分账"));
        } else {
            PostDivPayAdaptorDto dto = PostDivPayAdaptorDto.builder()
                    .transactionId(retObj.getTransaction_id())
                    .orderId(retObj.getOrder_id())
                    .build();
            return dto;
        }
    }


    /**
     * 请求分账回退API
     * https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter8_1_3.shtml
     * */
    @Override
    public PostRefundAdaptorDto createDivRefund(DivRefundTrans divRefundTrans) {
        DivPayTrans divPayTrans = divRefundTrans.getDivPayTrans();
        if(divPayTrans==null)
        {
            throw new IllegalArgumentException("createDivRefund: divPayTrans is null");
        }
        ShopChannel shopChannel = divRefundTrans.getShopChannel();
        if(shopChannel==null)
        {
            throw new IllegalArgumentException("createDivRefund: shopChannel is null");
        }
        Channel channel = shopChannel.getChannel();
        if(channel==null)
        {
            throw new IllegalArgumentException("createDivRefund: channel is null");
        }
        /*set param*/

        PostDivRefundParam param = PostDivRefundParam.builder()
                .sub_mchid(shopChannel.getSubMchid())
                .order_id(divPayTrans.getTransNo())
                .out_return_no(divRefundTrans.getOutNo())
                .return_mchid(channel.getSpMchid())
                .amount(divRefundTrans.getAmount())
                .build();

        String ret = this.wePayMapper.postDivRefund(param);
        PostDivRefundRetObj retObj = JacksonUtil.toObj(ret, PostDivRefundRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createPayment：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信分账退款"));
        } else {
            PostRefundAdaptorDto dto = PostRefundAdaptorDto.builder()
                    .transNo(retObj.getReturn_id())
                    .successTime(retObj.getFinish_time())
                    .userReceivedAccount(retObj.getUser_received_account())
                    .amount(retObj.getAmount())
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
            throw new IllegalArgumentException("createChannel: channel is null");
        }
        CancelDivParam param= CancelDivParam.builder()
                .sub_mchid(shopChannel.getSubMchid())
                .appid(channel.getSpAppid())
                .account(channel.getSpMchid())
                .build();
        String ret=this.wePayMapper.cancelDiv(param);
        CancelDivRetObj retObj=JacksonUtil.toObj(ret,CancelDivRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("cancelChannel：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信删除分账"));
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
            throw new IllegalArgumentException("createChannel: channel is null");
        }
        CreateDivParam param= CreateDivParam.builder()
                .sub_mchid(shopChannel.getSubMchid())
                .appid(channel.getSpAppid())
                .account(channel.getSpMchid())
                .build();
        String ret=this.wePayMapper.createDiv(param);
        CreateDivRetObj retObj = JacksonUtil.toObj(ret, CreateDivRetObj.class);
        if (null == retObj) {
            ExceptionRetObj exceptionRetObj = JacksonUtil.toObj(ret, ExceptionRetObj.class);
            logger.error("createChannel：param = {}, code = {}, message = {}", param, exceptionRetObj.getCode(), exceptionRetObj.getMessage());
            throw new BusinessException(ReturnNo.PAY_INVOKEAPI_ERROR, String.format(ReturnNo.PAY_INVOKEAPI_ERROR.getMessage(), "微信创建分账"));
        }
    }
}
