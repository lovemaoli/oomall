package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
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

@SpringBootTest(classes = AftersaleApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRED)
public class ArbitrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;
    private static String customerToken;
    private static String visitorToken;
    private static final String CREATE_AFTERSALE = "/aftersales/{aid}/arbitrations";
    private static final String DELETE_AFTERSALE = "/arbitrations/{aid}";
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        customerToken = jwtHelper.createToken(1L,"customer1",1L, 1, 3600);
        visitorToken=jwtHelper.createToken(2L,"visitor1",-1L,1,3600);
    }
//    @Test
//    //成功样例
//    void createArbitrationWhenUserSucceed() throws Exception {
//        String body = "{\"name\":\"test\", \"reason\": \"test_reason\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_AFTERSALE, 1L)
//                        .header("authorization", customerToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body));
////                .andExpect(MockMvcResultMatchers.status().isCreated())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customer_id", is(1L)))
////                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //用户无权限
//    void createArbitrationWhenVisitorUse()throws Exception{
//        String body = "{\"name\":\"test\", \"reason\": \"test_reason\"}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_AFTERSALE, 1L)
//                        .header("authorization", visitorToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(body));
////                .andExpect(MockMvcResultMatchers.status().isCreated())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
////                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //顾客取消仲裁成功
//    void deleteArbitrationWhenUserSucceed() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_AFTERSALE, 1L)
//                        .header("authorization", customerToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE));
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
////                .andDo(MockMvcResultHandlers.print());
//    }

}
