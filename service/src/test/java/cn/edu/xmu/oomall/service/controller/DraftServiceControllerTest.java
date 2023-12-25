package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceApplication;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
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
public class DraftServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;
    private static String validToken;
    private static String invalidToken;
    private static final String DRAFTSERVICE = "/maintainers/{mid}/region/{rid}/service";
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        validToken = jwtHelper.createToken(1L,"service_provider1",1L, 1, 3600);
        invalidToken=jwtHelper.createToken(2L,"visitor1",-1L,1,3600);
    }

    //定义成功
    void defServiceForProductInRegionWhenSucceed() throws Exception {
        String body = "{\"name\":\"test\", \"name\": \"维修\",\"description\": \"寄件维修\",\"type\": \"0\",\"category\": \"手机\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(DRAFTSERVICE, 4L)//不知道这个参数怎么填
                        .header("authorization", validToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(0)))
                .andDo(MockMvcResultHandlers.print());
    }


}
