//package cn.edu.xmu.oomall.product.controller;
//
//import cn.edu.xmu.javaee.core.mapper.RedisUtil;
//import cn.edu.xmu.javaee.core.model.InternalReturnObject;
//import cn.edu.xmu.javaee.core.util.JwtHelper;
//import cn.edu.xmu.oomall.product.ProductTestApplication;
//import cn.edu.xmu.oomall.product.mapper.openfeign.ShopMapper;
//import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
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
//@SpringBootTest(classes = ProductTestApplication.class)
//@AutoConfigureMockMvc
//@Transactional
//public class AdminGrouponActControllerTest2 {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private RedisUtil redisUtil;
//
//    @MockBean
//    @Autowired
//    private ShopMapper shopMapper;
//
//    private static String adminToken;
//
//    private static String GETGROUP = "/shops/{shopId}/groupons";
//    private static String GETGROUPID = "/shops/{shopId}/groupons/{id}";
//
//    @BeforeAll
//    public static void setup() {
//        JwtHelper jwtHelper = new JwtHelper();
//        adminToken = jwtHelper.createToken(1L, "13088admin", 0L, 1, 3600);
//    }
//
//    @Test
//    public void findGrouponByIdGivenNonExistId() throws Exception {
//        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
//        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
//        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
//
//        InternalReturnObject<Shop> retObj = new InternalReturnObject<>();
//        retObj.setErrno(0);
//        retObj.setErrmsg("成功");
//
//        Shop shop = new Shop();
//        shop.setId(2L);
//        shop.setName("商铺2");
//        retObj.setData(shop);
//
//        Mockito.when(shopMapper.getShopById(Mockito.anyLong())).thenReturn(retObj);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get(GETGROUPID, 2L, 400L)
//                        .header("authorization", adminToken)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        //.andDo(MockMvcResultHandlers.print());
//    }
//}
