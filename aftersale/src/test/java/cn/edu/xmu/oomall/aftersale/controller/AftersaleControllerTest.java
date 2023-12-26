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
public class AftersaleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String AFTERSALE = "/aftersales/{id}";

    private static String customerToken;
    private static String visitorToken;
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        customerToken = jwtHelper.createToken(1L,"customer1",1L, 1, 3600);
        visitorToken = jwtHelper.createToken(2L,"visitor1",-1L,1,3600);
    }

    @Test
    void findAftersaleByIdReturnsAftersaleWhenExists() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(AFTERSALE, 1L)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void findAftersaleByIdReturnsNotFoundWhenDoesNotExist() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(AFTERSALE, 2L)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findAftersaleByIdReturnsAftersaleNotLogin() throws Exception {
        //401 未登录
        this.mockMvc.perform(MockMvcRequestBuilders.get(AFTERSALE, 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
