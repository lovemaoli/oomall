package cn.edu.xmu.oomall.shop.controller;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.shop.ShopTestApplication;
import cn.edu.xmu.oomall.shop.controller.vo.PieceTemplateVo;
import cn.edu.xmu.oomall.shop.controller.vo.TemplateVo;
import cn.edu.xmu.oomall.shop.controller.vo.WeightTemplateVo;
import cn.edu.xmu.oomall.shop.mapper.openfeign.RegionMapper;
import cn.edu.xmu.oomall.shop.mapper.openfeign.po.RegionPo;
import cn.edu.xmu.oomall.shop.mapper.po.WeightThresholdPo;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author ChenLinghui
 * @Task 2023-dgn1-008
 */
@SpringBootTest(classes = ShopTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TemplateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;

    @MockBean
    private RegionMapper regionMapper;
    @MockBean
    private RocketMQTemplate rocketmq;

    private static String adminToken;
    private static String commonShopToken;

    /**
     * serUp用来准备各种情况下的Token
     */
    @BeforeAll
    static void setUp() {
        JwtHelper jwtHelper = new JwtHelper();
        adminToken = jwtHelper.createToken(1L, "Charley", 0L, 1, 3600);
        commonShopToken = jwtHelper.createToken(1L, "Charley", 2L, 1, 3600);
    }

    /**
     * 管理员定义运费模版，计件模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreateTemplateWhenPiece() throws Exception {
        //测试创建计件模板，成功创建
        TemplateVo vo = new TemplateVo();
        vo.setName("测试计件模版001");
        vo.setDefaultModel((byte) 1);
        vo.setType(1);
        vo.setDivideStrategy("cn.edu.xmu.oomall.shop.dao.bo.divide.MaxDivideStrategy");
        vo.setPackAlgorithm("cn.edu.xmu.oomall.shop.dao.bo.divide.SimpleAlgorithm");

        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates",1)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("测试计件模版001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    /**
     * 管理员定义运费模版，计重模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreateTemplateWhenWeight() throws Exception {
        TemplateVo vo = new TemplateVo();
        vo.setDefaultModel((byte) 1);
        vo.setType(0);
        vo.setName("测试计重模版001");
        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates",1)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("测试计重模版001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }

    /**
     * 非管理员定义运费模版，计重模板，
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreateTemplateWhenNotAdmin() throws Exception {
        TemplateVo vo = new TemplateVo();
        vo.setDefaultModel((byte) 1);
        vo.setType(0);
        vo.setName("测试计重模版001");
        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates",1)
                        .header("authorization", commonShopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));

    }
    /**
     * 以名字获取商品运费模版
     * @throws Exception
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testRetrieveTemplateByName()throws Exception{
        //带有参数的查询
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/{shopId}/templates",3)
                        .header("authorization", adminToken)
                        .param("name","贪心计重模板")
                        .param("page","1")
                        .param("pageSize","5"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '18')].name", hasItem("贪心计重模板")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
    /**
     * 获取商品运费模版，name字段为空
     * @throws Exception
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testRetrieveTemplateByNameWhenNameNull()throws Exception{
        //不带参数的查询
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/{shopId}/templates",1)
                        .header("authorization", adminToken)
                        .param("name","")
                        .param("page","1")
                        .param("pageSize","5"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].name", hasItem("最大简单分包计重模板")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].name", hasItem("平均背包分包计件模板")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    /**
     * 管理员克隆运费模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     *
     */
    @Test
    void testCloneTemplate()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/clone",1,1)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    /**
     * 管理员克隆运费模板，传参模板不存在
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     *
     */
    @Test
    void testCloneTemplateWhenSourceNull()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/clone",1,7)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }
    /**
     * 非管理员克隆运费模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     *
     */
    @Test
    void testCloneTemplateWhenNotAdmin()throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/clone",1,16)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }
    /**
     * 获得运费模板详情
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testFindTemplateById()throws Exception{


        this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/{shopId}/templates/{id}",1,2)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("平均背包分包计件模板")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
    /**
     * 管理员修改运费模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testUpdateTemplateById()throws Exception{
        TemplateVo vo =new TemplateVo();
        vo.setType(1);
        vo.setName("新的模板名称");
        vo.setDefaultModel((byte) 1);
        String body = JacksonUtil.toJson(vo);


        this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/templates/{id}",1,2)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
    /**
     * 管理员修改运费模板，传参模板不存在
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testUpdateTemplateByIdWhenNotExist()throws Exception{
        TemplateVo vo =new TemplateVo();
        vo.setType(1);
        vo.setName("新的模板名称");
        vo.setDefaultModel((byte) 1);
        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/templates/{id}",1,3)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));

    }
    /**
     * 删除运费模板，且同步删除与商品的关系
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testDeleteTemplate()throws Exception{
        TemplateVo vo =new TemplateVo();

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/shops/{shopId}/templates/{id}",1,2)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));

    }
    /**
     * 管理员定义重量模板明细
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreateWeightTemplate()throws Exception{
        WeightTemplateVo vo =new WeightTemplateVo();
        List<WeightThresholdPo> thresholds = new ArrayList<>();

        WeightThresholdPo weightThresholdPo = new WeightThresholdPo();
        weightThresholdPo.setBelow(10);
        weightThresholdPo.setPrice(10L);
        thresholds.add(weightThresholdPo);

        weightThresholdPo.setBelow(20);
        weightThresholdPo.setPrice(20L);
        thresholds.add(weightThresholdPo);

        vo.setThresholds(thresholds);
        vo.setFirstWeight(3);
        vo.setFirstWeightFreight(3L);
        vo.setUnit(2);
        vo.setUpperLimit(10);

        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(248059L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        String body = JacksonUtil.toJson(vo);

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/regions/{rid}/weighttemplate",1,1,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    /**
     * 管理员定义重量模板明细且传参模板类型不匹配
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreateWeightTemplateWhenNotMatch()throws Exception{
        WeightTemplateVo vo =new WeightTemplateVo();
        List<WeightThresholdPo> thresholds = new ArrayList<>();

        WeightThresholdPo weightThresholdPo = new WeightThresholdPo();
        weightThresholdPo.setBelow(10);
        weightThresholdPo.setPrice(10L);
        thresholds.add(weightThresholdPo);

        weightThresholdPo.setBelow(20);
        weightThresholdPo.setPrice(20L);
        thresholds.add(weightThresholdPo);

        vo.setThresholds(thresholds);
        vo.setFirstWeight(3);
        vo.setFirstWeightFreight(3L);
        vo.setUnit(2);
        vo.setUpperLimit(10);

        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(248059L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        String body = JacksonUtil.toJson(vo);

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/regions/{rid}/weighttemplate",1,2,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FREIGHT_TEMPLATENOTMATCH.getErrNo())));

    }
    /**
     * 管理员修改重量模板明细
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testUpdateWeightTemplate()throws Exception{
        WeightTemplateVo vo =new WeightTemplateVo();
        List<WeightThresholdPo> thresholds = new ArrayList<>();

        WeightThresholdPo weightThresholdPo = new WeightThresholdPo();
        weightThresholdPo.setBelow(10);
        weightThresholdPo.setPrice(10L);
        thresholds.add(weightThresholdPo);

        weightThresholdPo.setBelow(20);
        weightThresholdPo.setPrice(20L);
        thresholds.add(weightThresholdPo);

        vo.setThresholds(thresholds);
        vo.setFirstWeight(3);
        vo.setFirstWeightFreight(3L);

        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(251197L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        String body = JacksonUtil.toJson(vo);



        this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/templates/{id}/regions/{rid}/weighttemplate",1,1,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));



    }
    /**
     * 管理员删除地区模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testDeleteRegionTemplate()throws Exception{


        this.mockMvc.perform(MockMvcRequestBuilders.delete("/shops/{shopId}/templates/{id}/regions/{rid}",1,1,248059)
                        .header("authorization", adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));



    }
    /**
     * 管理员定义件数模板明细
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreatePieceTemplate()throws Exception{
        PieceTemplateVo vo =new PieceTemplateVo();

        vo.setAdditionalItems(2);
        vo.setFirstItem(2);
        vo.setAdditionalItemsPrice(4L);
        vo.setFirstItemPrice(1L);
        vo.setUnit(2);
        vo.setUpperLimit(10);


        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(248059L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        String body = JacksonUtil.toJson(vo);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/regions/{rid}/piecetemplates",1,2,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));

    }
    /**
     * 管理员定义件数模板明细且传入为重量模版类型
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testCreatePieceTemplateWhenNotMatch()throws Exception{
        PieceTemplateVo vo =new PieceTemplateVo();

        vo.setAdditionalItems(2);
        vo.setFirstItem(2);
        vo.setAdditionalItemsPrice(4L);
        vo.setFirstItemPrice(1L);
        vo.setUnit(2);
        vo.setUpperLimit(10);


        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(248059L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/regions/{rid}/piecetemplates",1,1,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FREIGHT_TEMPLATENOTMATCH.getErrNo())));

    }
    /**
     * 管理员修改件数模板
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testUpdatePieceTemplate()throws Exception{
        PieceTemplateVo vo =new PieceTemplateVo();

        vo.setAdditionalItems(2);
        vo.setFirstItem(2);
        vo.setAdditionalItemsPrice(4L);
        vo.setFirstItemPrice(1L);

        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(251197L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        String body = JacksonUtil.toJson(vo);



        this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/templates/{id}/regions/{rid}/piecetemplates",1,2,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));


    }
    /**
     * 管理员修改件数模板且传参模板类型不匹配
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testUpdatePieceTemplateWhenNotMatch()throws Exception{
        PieceTemplateVo vo =new PieceTemplateVo();

        vo.setAdditionalItems(2);
        vo.setFirstItem(2);
        vo.setAdditionalItemsPrice(4L);
        vo.setFirstItemPrice(1L);

        InternalReturnObject<RegionPo> ret = new InternalReturnObject<RegionPo>();
        RegionPo regionPo = new RegionPo();
        regionPo.setId(251197L);
        regionPo.setName("测试地区的名字");
        ret.setData(regionPo);
        ret.setErrno(ReturnNo.OK.getErrNo());
        ret.setErrmsg(ReturnNo.OK.getMessage());

        Mockito.when(regionMapper.findRegionById(Mockito.eq(248059L))).thenReturn(ret);

        String body = JacksonUtil.toJson(vo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/templates/{id}/regions/{rid}/piecetemplates",1,1,248059)
                        .header("authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FREIGHT_TEMPLATENOTMATCH.getErrNo())));

    }
    /**
     * 店家或管理员查询运费模板明细
     * @author ChenLinghui
     * @Task 2023-dgn1-008
     */
    @Test
    void testRetrieveRegionTemplateById()throws Exception{



        this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/{shopId}/templates/{id}/regions",1,2)
                        .header("authorization", adminToken)
                        .param("page","1").param("pageSize","5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '105')].regionId",hasItem(248059)))

                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));


    }


























}
