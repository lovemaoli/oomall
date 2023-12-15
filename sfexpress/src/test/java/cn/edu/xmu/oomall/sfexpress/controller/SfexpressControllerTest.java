package cn.edu.xmu.oomall.sfexpress.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.sfexpress.SfExpressApplication;
import cn.edu.xmu.oomall.sfexpress.controller.vo.*;
import cn.edu.xmu.oomall.sfexpress.exception.SFErrorCodeEnum;
import cn.edu.xmu.oomall.sfexpress.utils.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = SfExpressApplication.class)
@AutoConfigureMockMvc
@Transactional
public class SfexpressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final IdWorker idWorker = new IdWorker(1L, 1L);

    private static String adminToken;

    private final String SFURL = "/internal/sf/";

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        JwtHelper jwtHelper = new JwtHelper();
        adminToken = jwtHelper.createToken(1L, "13088admin", 0L, 1, 3600);
    }

    @Test
    void createExpressTestWhenSuccess() throws Exception {
        Long id = idWorker.nextId();
        String body = "{" +
                "\"partnerID\": \"合作伙伴编码\"," +
                "\"requestID\": \"请求唯一号UUID\"," +
                "\"serviceCode\": \"EXP_RECE_CREATE_ORDER\"," +
                "\"timestamp\": \"调用接口时间戳\"," +
                "\"msgDigest\": \"数字签名\"," +
                "\"msgData\": {" +
                "\"language\": \"zh-CN\"," +
                "\"orderId\": \"" + id.toString() + "\"," +
                "\"cargoDetails\": [" +
                "{\"name\": \"商品1\"}," +
                "{\"name\": \"商品2\"}" +
                "]," +
                "\"cargoDesc\": \"电子产品\"," +
                "\"contactInfoList\": [" +
                "{\"contact\": \"发件人\",\"contactType\": \"1\",\"mobile\": \"13812345678\",\"address\": \"发件人地址\", \"city\": \"769\"}," +
                "{\"contact\": \"收件人\",\"contactType\": \"2\",\"mobile\": \"13987654321\",\"address\": \"收件人地址\", \"city\": \"20\"}" +
                "]," +
                "\"monthlyCard\": \"SF123456789\"," +
                "\"payMethod\": 1," +
                "\"expressTypeId\": 123" +
                "}" +
                "}";

        System.out.println(mockMvc.getClass());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/internal/sf/")
//                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"));
    }

    @Test
    void createExpressTestWhenOrderIdRepeatThenFailed() throws Exception {
        String body = "{" +
                "\"partnerID\": \"合作伙伴编码\"," +
                "\"requestID\": \"请求唯一号UUID\"," +
                "\"serviceCode\": \"EXP_RECE_CREATE_ORDER\"," +
                "\"timestamp\": \"调用接口时间戳\"," +
                "\"msgDigest\": \"数字签名\"," +
                "\"msgData\": {" +
                "\"language\": \"zh-CN\"," +
                "\"orderId\": \"20231211001\"," +
                "\"cargoDetails\": [" +
                "{\"name\": \"商品1\"}," +
                "{\"name\": \"商品2\"}" +
                "]," +
                "\"cargoDesc\": \"电子产品\"," +
                "\"contactInfoList\": [" +
                "{\"contact\": \"发件人\",\"contactType\": \"1\",\"mobile\": \"13812345678\",\"address\": \"发件人地址\"}," +
                "{\"contact\": \"收件人\",\"contactType\": \"2\",\"mobile\": \"13987654321\",\"address\": \"收件人地址\"}" +
                "]," +
                "\"monthlyCard\": \"SF123456789\"," +
                "\"payMethod\": 1," +
                "\"expressTypeId\": 123" +
                "}" +
                "}";

        System.out.println(mockMvc.getClass());

        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
//                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8016.getErrorCodeString()));
    }
    
    @Test
    void searchOrderWhenSuccess() throws Exception {
        PostSearchOrderVo postSearchOrderVo = new PostSearchOrderVo();
        postSearchOrderVo.setOrderId("20231211001");
        SFPostRequestVo<PostSearchOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postSearchOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_SEARCH_ORDER_RESP");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value("S0000"));
    }

    @Test
    void searchOrderWhenOrderIdNotExist() throws Exception {
        PostSearchOrderVo postSearchOrderVo = new PostSearchOrderVo();
        postSearchOrderVo.setOrderId("11111111");
        SFPostRequestVo<PostSearchOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postSearchOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_SEARCH_ORDER_RESP");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8024.getErrorCodeString()));
    }

    @Test
    void searchRouteWhenUseMailNo() throws Exception {
        PostSearchRoutesVo postSearchRoutesVo = new PostSearchRoutesVo();
        postSearchRoutesVo.setTrackingType(1);
        String mailNo = "SF1183896766740172800";
        List<String> trackingNumberList = new ArrayList<>();
        trackingNumberList.add(mailNo);
        postSearchRoutesVo.setTrackingNumber(trackingNumberList);
        SFPostRequestVo<PostSearchRoutesVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postSearchRoutesVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_SEARCH_ROUTES");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value("S0000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.routeResps[0].mailNo").value("SF1183896766740172800"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.routeResps[0].routes.length").value(3));
    }

    @Test
    void searchRouteWhenUseOrderId() throws Exception {
        PostSearchRoutesVo postSearchRoutesVo = new PostSearchRoutesVo();
        postSearchRoutesVo.setTrackingType(2);
        String orderId = "1183896766073278464";
        List<String> trackingNumberList = new ArrayList<>();
        trackingNumberList.add(orderId);
        postSearchRoutesVo.setTrackingNumber(trackingNumberList);
        SFPostRequestVo<PostSearchRoutesVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postSearchRoutesVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_SEARCH_ROUTES");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value("S0000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.routeResps[0].mailNo").value("SF1183896766740172800"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.routeResps[0].routes.length").value(3));
    }

    @Test
    void searchRouteWhenMailNoNotExist() throws Exception {
        PostSearchRoutesVo postSearchRoutesVo = new PostSearchRoutesVo();
        postSearchRoutesVo.setTrackingType(1);
        String mailNo = "1231231231";
        List<String> trackingNumberList = new ArrayList<>();
        trackingNumberList.add(mailNo);
        postSearchRoutesVo.setTrackingNumber(trackingNumberList);
        SFPostRequestVo<PostSearchRoutesVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postSearchRoutesVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_SEARCH_ROUTES");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorMsg").value(SFErrorCodeEnum.E8024.getErrorDescAndAdvice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.routeResps[0].routes.length").value(3));
    }

    @Test
    void updateExpressTest() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(1);
        postUpdateOrderVo.setOrderId("1183896766073278464");
        postUpdateOrderVo.setTotalHeight(10.000);
        postUpdateOrderVo.setTotalLength(10.000);
        postUpdateOrderVo.setTotalVolume(10.000);
        postUpdateOrderVo.setTotalWeight(10.000);
        postUpdateOrderVo.setTotalWidth(10.000);
        postUpdateOrderVo.setSendStartTm(new Timestamp((new Date()).getTime()));
        postUpdateOrderVo.setPickupAppointEndtime(new Timestamp((new Date()).getTime()));
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.orderId").value("1183896766073278464"));
    }

    @Test
    void updateExpressTestWhenOrderHasBeanCanceled() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(1);
        postUpdateOrderVo.setOrderId("1183895391419502592");
        postUpdateOrderVo.setTotalHeight(10.000);
        postUpdateOrderVo.setTotalLength(10.000);
        postUpdateOrderVo.setTotalVolume(10.000);
        postUpdateOrderVo.setTotalWeight(10.000);
        postUpdateOrderVo.setTotalWidth(10.000);
        postUpdateOrderVo.setSendStartTm(new Timestamp((new Date()).getTime()));
        postUpdateOrderVo.setPickupAppointEndtime(new Timestamp((new Date()).getTime()));
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8037.getErrorCodeString()));
    }

    @Test
    void updateExpressTestWhenOrderHasBeanChecked() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(1);
        postUpdateOrderVo.setOrderId("1183893848351838208");
        postUpdateOrderVo.setTotalHeight(10.000);
        postUpdateOrderVo.setTotalLength(10.000);
        postUpdateOrderVo.setTotalVolume(10.000);
        postUpdateOrderVo.setTotalWeight(10.000);
        postUpdateOrderVo.setTotalWidth(10.000);
        postUpdateOrderVo.setSendStartTm(new Timestamp((new Date()).getTime()));
        postUpdateOrderVo.setPickupAppointEndtime(new Timestamp((new Date()).getTime()));
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8252.getErrorCodeString()));
    }

    @Test
    void cancelExpressTest() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(2);
        postUpdateOrderVo.setOrderId("1183896766073278464");
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.msgData.resStatus").value(2));
    }

    @Test
    void cancelExpressWhenHasBeanChecked() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(2);
        postUpdateOrderVo.setOrderId("1183893848351838208");
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8252.getErrorCodeString()));
    }

    @Test
    void cancelExpressWhenHasBeanCanceled() throws Exception {
        PostUpdateOrderVo postUpdateOrderVo = new PostUpdateOrderVo();
        postUpdateOrderVo.setDealType(2);
        postUpdateOrderVo.setOrderId("1183895391419502592");
        SFPostRequestVo<PostUpdateOrderVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(postUpdateOrderVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("EXP_RECE_UPDATE_ORDER");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.errorCode").value(SFErrorCodeEnum.E8037.getErrorCodeString()));
    }

    @Test
    void printWaybills() throws Exception {
        PostPrintWaybillsVo printWaybillsVo = new PostPrintWaybillsVo();
        PostPrintWaybillsVo.DocumentsDTO documentsDTO = new PostPrintWaybillsVo.DocumentsDTO();
        documentsDTO.setMasterWaybillNo("SF1183896766740172800");
        List<PostPrintWaybillsVo.DocumentsDTO> documentsDTOList = new ArrayList<>();
        documentsDTOList.add(documentsDTO);
        printWaybillsVo.setDocuments(documentsDTOList);
        SFPostRequestVo<PostPrintWaybillsVo> sfPostRequestVo = new SFPostRequestVo<>();
        sfPostRequestVo.setMsgData(printWaybillsVo);
        sfPostRequestVo.setRequestID(idWorker.nextId().toString());
        sfPostRequestVo.setServiceCode("COM_RECE_CLOUD_PRINT_WAYBILLS");
        sfPostRequestVo.setTimestamp(String.valueOf(new Timestamp((new Date()).getTime())));
        String body = objectMapper.writeValueAsString(sfPostRequestVo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(SFURL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultCode").value("A1000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiResultData.success").value(true));
    }
}
