package cn.edu.xmu.oomall.freight.mapper.openfeign.zt;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 创建订单接口信息类
 * @Author 李子晴
 * 2023-dgn2-001
 */
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateExpressOrderParam {
    
    /*
     * 合作模式 ，1：集团客户；2：非集团客户
     * OOMALL合作模式均为非集团客户,
     */
    private String partnerType="2";

    /*
     * orderType：1：全网件 2：预约件（返回运单号） 3：预约件（不返回运单号） 4：星联全网件、
     * 默认为1
     * 这个要改，是预约件，需要增加accountInfo，否则无法实现结算,其中
     * accountId 电子面单账号（partnerType为2，orderType传1,2,4时必传）
     * accountPassword是必传的
     */

    private String orderType="1";


    /*
     * 合作商订单号，商家自主定义
     * 对应Express-orderCode
     */
    private String partnerOrderCode;

    /*
     * 发件人信息类
     */
    private SenderInfo senderObject;

    /*
     * 收件人信息类
     */
    private ReceiveInfo receiverObject;

}
