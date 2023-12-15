//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao.channel;

import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.dao.channel.dto.*;

import java.util.List;

/**
 * 支付渠道适配器接口
 * 适配器模式
 */
public interface PayAdaptor {

    /**
     * 创建支付交易单
     * @author Ming Qiu
     * <p>
     * date: 2022-11-01 19:26
     * @param payTrans 支付交易
     * @return
     */
    PostPayTransAdaptorDto createPayment(PayTrans payTrans);

    /**
     * 向第三方平台查询订单
     * */
    PayTrans returnOrderByTransId(ShopChannel shopChannel, String transId);

    /**
     * 向第三方平台查询订单(未收到下单返回值)
     * */
    PayTrans returnOrderByOutNo(ShopChannel shopChannel, String outNo);

    /**
     * 取消订单
     * */
    void cancelOrder(PayTrans payTrans);


    PostRefundAdaptorDto createRefund(RefundTrans refundTrans);

    /**
     * 查询退款单
     * */
    RefundTrans returnRefund(ShopChannel shopChannel, String outNo);

    /**
     * 分账交易
     * */
    PostDivPayAdaptorDto createDivPay(PayTrans payTrans,  String outNo);


    /**
     * 退款分账交易
     * */
    PostRefundAdaptorDto createDivRefund(DivRefundTrans divRefundTrans);


    /**
     * 分账关系解绑
     * @param shopChannel
     */
    void cancelChannel(ShopChannel shopChannel);

    /**
     * 建立分账关系
     * @param shopChannel
     */
    void createChannel(ShopChannel shopChannel);
}
