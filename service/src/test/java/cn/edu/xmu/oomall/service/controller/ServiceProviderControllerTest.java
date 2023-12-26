package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceApplication;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
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
public class ServiceProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static String validToken;
    private static String invalidToken;
    private static final String MAINTAINER = "/maintainers/{id}";
    private static final String MAINTAINERCANCEL = "/adminusers/{aid}/maintainers/{mid}/cancel";
    private static final String MAINTAINERRESUME = "/adminusers/{aid}/maintainers/{mid}/resume";
    private static final String MAINTAINERSUSPEND = "/adminusers/{aid}/maintainers/{mid}/suspend";
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        validToken = jwtHelper.createToken(1L,"admin1",1L, 1, 3600);
        invalidToken = jwtHelper.createToken(2L,"visitor",-1L, 1, 3600);
    }
//    @Test
//    //成功查询
//    void findServiceProviderByIdWhenExists() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get(MAINTAINER, 1L)
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    //查询ID不匹配
//    void findServiceProviderByIdWhenDoesNotMatch() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get(MAINTAINER, 2L)
//                        .header("authorization", invalidToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //成功取消
//    void cancelServiceProviderWhenSucceed() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    //用户无权限
//    void cancelServiceProviderWhenHasNoAuth() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", invalidToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//        //成功恢复
//    void resumeServiceProviderWhenSucceed() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//        //用户无权限
//    void resumeServiceProviderWhenHasNoAuth() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", invalidToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//        //成功暂停
//    void suspendServiceProviderWhenSucceed() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", validToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//        //用户无权限
//    void suspendServiceProviderWhenHasNoAuth() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put(MAINTAINER, 1L)
//                        .header("authorization", invalidToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())))
//                .andDo(MockMvcResultHandlers.print());
//    }

}
