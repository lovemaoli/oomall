package cn.edu.xmu.oomall.payment.dao.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求分账
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDivPayAdaptorDto {
    /**
     * 支付订单号
     */
    private String transactionId;
    /**
     * 分账单号
     */
    private String orderId;
}
