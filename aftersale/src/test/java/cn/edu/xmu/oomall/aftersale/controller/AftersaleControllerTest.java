package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
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
    @MockBean
    private RedisUtil redisUtil;
    private static final String AFTERSALE = "/aftersales/{id}";

    @Test
    void findAftersaleByIdReturnsAftersaleWhenExists() throws Exception {
//        Aftersale aftersale = new Aftersale();
//        aftersale.setId(1L);
//        ArbitrationDao aftersaleService = Mockito.mock(ArbitrationDao.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get(AFTERSALE, 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void findAftersaleByIdReturnsNotFoundWhenDoesNotExist() throws Exception {
//        ArbitrationDao aftersaleService = Mockito.mock(ArbitrationDao.class);
//        Mockito.when(aftersaleService.findById(1L)).thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders.get(AFTERSALE, 2L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
