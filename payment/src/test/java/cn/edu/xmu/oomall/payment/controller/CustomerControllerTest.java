package cn.edu.xmu.oomall.payment.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.payment.PaymentApplication;
import cn.edu.xmu.oomall.payment.controller.vo.AlipayNotifyVo;
import cn.edu.xmu.oomall.payment.controller.vo.WepayNotifyVo;
import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author ych
 * task 2023-dgn1-004
 */

@SpringBootTest(classes = PaymentApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RedisUtil redisUtil;
    private final String ALIPAY_NOTIFY_URL = "/notify/payments/alipay";
    private final String WEPAY_NOTIFY_URL = "/notify/payments/wepay";


    /**
     * task 2023-dgn1-004
     * @author ych
     * 状态为 TRADE_CLOSED
     */
    @Test
    public void testAlipayNotifyWhenStateIsTRADE_CLOSED() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime localDateTime = LocalDateTime.parse("2018-06-08T10:34:56+08:00", formatter);

        AlipayNotifyVo alipayNotifyVo = new AlipayNotifyVo();
        alipayNotifyVo.setAppId("20214072300007148");
        alipayNotifyVo.setTradeNo("2013112011001004330000121536");
        alipayNotifyVo.setOutTradeNo("6823789339978248");
        alipayNotifyVo.setGmtPayment(localDateTime);
        alipayNotifyVo.setTradeStatus("TRADE_CLOSED");
        alipayNotifyVo.setReceiptAmount(100L);

        String AliPayVoJson = objectMapper.writeValueAsString(alipayNotifyVo);

        Mockito.when(redisUtil.hasKey( Mockito.any())).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.post(ALIPAY_NOTIFY_URL)
                        .content(AliPayVoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno").value(0));
    }

    /**
     * task 2023-dgn1-004
     * @author ych
     * 状态为 TRADE_SUCCESS
     */
    @Test
    public void testAlipayNotifyWhenStateIsTRADE_SUCCESS() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime localDateTime = LocalDateTime.parse("2018-06-08T10:34:56+08:00", formatter);

        AlipayNotifyVo alipayNotifyVo = new AlipayNotifyVo();
        alipayNotifyVo.setAppId("20214072300007148");
        alipayNotifyVo.setTradeNo("2013112011001004330000121536");
        alipayNotifyVo.setOutTradeNo("6823789339978248");
        alipayNotifyVo.setGmtPayment(localDateTime);
        alipayNotifyVo.setTradeStatus("TRADE_SUCCESS");
        alipayNotifyVo.setReceiptAmount(100L);

        String AliPayVoJson = objectMapper.writeValueAsString(alipayNotifyVo);

        this.mockMvc.perform(MockMvcRequestBuilders.post(ALIPAY_NOTIFY_URL)
                        .content(AliPayVoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno").value(0));
    }

    /**
     * task 2023-dgn1-004
     * @author ych
     * 其他状态
     */
    @Test
    public void testAlipayNotifyWhenStateIsOtherState() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime localDateTime = LocalDateTime.parse("2018-06-08T10:34:56+08:00", formatter);

        AlipayNotifyVo alipayNotifyVo = new AlipayNotifyVo();
        alipayNotifyVo.setAppId("20214072300007148");
        alipayNotifyVo.setTradeNo("2013112011001004330000121536");
        alipayNotifyVo.setOutTradeNo("6823789339978248");
        alipayNotifyVo.setGmtPayment(localDateTime);
        alipayNotifyVo.setTradeStatus("WAIT_BUYER_PAY");
        alipayNotifyVo.setReceiptAmount(100L);

        String AliPayVoJson = objectMapper.writeValueAsString(alipayNotifyVo);

        this.mockMvc.perform(MockMvcRequestBuilders.post(ALIPAY_NOTIFY_URL)
                        .content(AliPayVoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno").value(0));
    }

    /**
     * task 2023-dgn1-004
     * @author ych
     */
    @Test
    public void testWepayNotifyGivenRightArgs() throws Exception {

        WepayNotifyVo wepayNotifyVo = new WepayNotifyVo();
        WepayNotifyVo.WePayResource wePayResource = wepayNotifyVo.new WePayResource();
        WepayNotifyVo.WePayResource.Amount amount = wePayResource.new Amount();
        WepayNotifyVo.WePayResource.Payer payer = wePayResource.new Payer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime localDateTime = LocalDateTime.parse("2018-06-08T10:34:56+08:00", formatter);

        amount.setTotal(100L);
        amount.setPayerTotal(100L);
        payer.setSpOpenId("oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");

        wePayResource.setSpAppId("wx8888888888888888");
        wePayResource.setSpMchId("1230000109");
        wePayResource.setSubMchId("1900000109");
        wePayResource.setOutTradeNo("1217752501201407033233368018");
        wePayResource.setTransactionId("1217752501201407033233368018");
        wePayResource.setTradeState("SUCCESS");
        wePayResource.setSuccessTime(localDateTime);
        wePayResource.setAmount(amount);
        wePayResource.setPayer(payer);

        wepayNotifyVo.setId("EV-2018022511223320873");
        wepayNotifyVo.setCreateTime("2015-05-20T13:29:35+08:00");
        wepayNotifyVo.setResource(wePayResource);

        String WePayVoJson = objectMapper.writeValueAsString(wepayNotifyVo);

        this.mockMvc.perform(MockMvcRequestBuilders.post(WEPAY_NOTIFY_URL)
                        .content(WePayVoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno").value(0));
    }
    private final String CUSTOMER_GET_CHANNELS="/channels";
    @Test
    void retrieveChannel1() throws Exception
    {
        this.mockMvc.perform((MockMvcRequestBuilders.get(CUSTOMER_GET_CHANNELS).
                        param("shopId", "0").
                        param("page","1").
                        param("pageSize","100")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(jsonPath("$.data.list[?(@.id == '501')].name", hasItem("微信支付")))
                .andExpect(jsonPath("$.data.list[?(@.id == '502')].name", hasItem("支付宝")));
    }

    @Test
    void retrieveChannel2() throws Exception
    {
        this.mockMvc.perform((MockMvcRequestBuilders.get(CUSTOMER_GET_CHANNELS).
                        param("shopId", "1").
                        param("page","1").
                        param("pageSize","100")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(jsonPath("$.data.list[?(@.id == '501')].name", hasItem("微信支付")))
                .andExpect(jsonPath("$.data.list[?(@.id == '502')].name", hasItem("支付宝")));
    }
}
