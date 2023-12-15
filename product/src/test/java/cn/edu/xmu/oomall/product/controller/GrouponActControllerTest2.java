////School of Informatics Xiamen University, GPL-3.0 license
//
//package cn.edu.xmu.oomall.product.controller;
//
//import cn.edu.xmu.javaee.core.model.InternalReturnObject;
//import cn.edu.xmu.javaee.core.model.ReturnNo;
//import cn.edu.xmu.javaee.core.util.JwtHelper;
//import cn.edu.xmu.javaee.core.mapper.RedisUtil;
//import cn.edu.xmu.oomall.product.ProductTestApplication;
//import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
//import cn.edu.xmu.oomall.product.mapper.openfeign.ShopMapper;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.hamcrest.CoreMatchers.is;
//
//@SpringBootTest(classes = ProductTestApplication.class)
//@AutoConfigureMockMvc
//@Transactional
//public class GrouponActControllerTest2 {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private RedisUtil redisUtil;
//
//    @MockBean
//    private ShopMapper shopMapper;
//
//    private static String adminToken, shopToken1, shopToken2, shopToken3;
//
//    @BeforeAll
//    public static void setup() {
//        JwtHelper jwtHelper = new JwtHelper();
//        adminToken = jwtHelper.createToken(1L, "admin", 0L, 1, 3600);
//        shopToken1 = jwtHelper.createToken(1L, "wt", 10L, 1, 3600);
//        shopToken2 = jwtHelper.createToken(1L, "wt", 3L, 1, 3600);
//        shopToken3 = jwtHelper.createToken(1L, "wt", 2L, 1, 3600);
//    }
//
//    @Test
//    public void createGrouponActTest()throws Exception{
//        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
//        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
//        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
//        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
//
//        String requestJson = "{\"name\":\"团购活动1\","
//                + "\"thresholds\":[{\"quantity\":\"100\", \"percentage\":\"10\"},{\"quantity\":\"50\",\"percentage\":\"5\"}],"
//                + "\"price\":\"10000\",\"price\":\"10000\",\"quantity\":\"10000\",\"max_quantity\":\"200\","
//                + "\"beginTime\":\"3030-12-05T10:09:50.000\",\"endTime\":\"3032-12-05T10:09:50.000\""
//                + "}";
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/products/{pid}/groupons", 3L, 1755L)
//                        .header("authorization", shopToken2)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("团购活动1")));
//        ////.andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void delGrouponActTest() throws Exception {
//        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
//        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
//        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
//        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
//        this.mockMvc.perform(MockMvcRequestBuilders.delete("/shops/{shopId}/groupons/{id}", 0, 4L)
//                        .header("authorization", adminToken))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
//        ////.andDo(MockMvcResultHandlers.print());
//    }
//
//
//    @Test
//    public void updateGrouponActByShopIdAndActIdTest() throws Exception {
//        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
//        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
//        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
//        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
//        String requestJson="{\"name\": \"团购活动1\", \"thresholds\": [{\"quantity\":\"100\", \"percentage\":\"10\"},{\"quantity\":\"50\",\"percentage\":\"5\"}],"
//                + "\"beginTime\":\"3030-12-05T10:09:50.000\",\"endTime\":\"3032-12-05T10:09:50.000\""
//                + "}";
//        this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/groupons/{id}", 2, 4)
//                        .header("authorization", adminToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(requestJson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
//        ////.andDo(MockMvcResultHandlers.print());
//    }
//
//    @Test
//    public void retrieveGrouponActTest() throws Exception {
//        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
//        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
//        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
//        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/groupons")
//                        .header("authorization", adminToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .param("shopId", "4")
//                        .param("productId", "1576")
//                        .param("page", "1")
//                        .param("pageSize", "10"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(0)));
//        ////.andDo(MockMvcResultHandlers.print());
//    }
//
//}
