package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceApplication;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.Service;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = ServiceApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;
    private static String validToken;
    private static final String MAINTAINERRECEIVE ="/maintainers/{mid}/receive";
    private static final String MAINTAINERDELETE ="/maintainers/{mid}/serviceorders/{id}/cancelorder";
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        validToken = jwtHelper.createToken(1L,"service_provider1",1L, 1, 3600);
    }
//    @Test
//    //成功收件
//    void serviceProviderReceiveWhenSucceed() throws Exception {
//        String body = "{\"name\":\"test\", \"billcode\": \"32163721778\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINERRECEIVE, 1L)//不知道这个参数怎么填
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //单号为空
//    void serviceProviderReceiveWhenBillcodeIsNull() throws Exception {
//        String body = "{\"name\":\"test\", \"billcode\": \"\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINERRECEIVE, 1L)//不知道这个参数怎么填
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.PARAMETER_MISSED.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //成功取消
//    void serviceProviderCancelOrderWhenSucceed() throws Exception {
//        String body = "{\"name\":\"test\", \"reason\": \"暂停服务\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINERDELETE, 1L)//不知道这个参数怎么填
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //取消理由为空
//    void serviceProviderReceiveWhenReasonIsNull() throws Exception {
//        String body = "{\"name\":\"test\", \"reason\": \"\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINERDELETE, 1L)//不知道这个参数怎么填
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.PARAMETER_MISSED.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
}
