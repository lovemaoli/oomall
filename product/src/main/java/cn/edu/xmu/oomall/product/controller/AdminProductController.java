//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller;


import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.product.controller.dto.*;
import cn.edu.xmu.oomall.product.controller.vo.*;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.oomall.product.dao.bo.*;
import cn.edu.xmu.oomall.product.service.OnsaleService;
import cn.edu.xmu.oomall.product.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * 商品控制器
 * @author Ming Qiu
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{shopId}", produces = "application/json;charset=UTF-8")
public class AdminProductController{

    private final Logger logger = LoggerFactory.getLogger(AdminProductController.class);


    private OnsaleService onsaleService;
    private ProductService productService;

    private TransactionTemplate transactionTemplate;
    @Autowired
    public AdminProductController(ProductService productService, OnsaleService onSaleService, TransactionTemplate transactionTemplate) {
        this.productService = productService;
        this.onsaleService=onSaleService;
        this.transactionTemplate = transactionTemplate;
    }

    @GetMapping("/onsales/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "shops")
    public ReturnObject getOnsaleById(@PathVariable Long shopId, @PathVariable Long id) {
        OnSale onsale = this.onsaleService.findById(shopId, id);
        return new ReturnObject(CloneFactory.copy(new OnsaleDto(), onsale));
    }

    @PostMapping("/products/{id}/onsales")
    @Audit(departName = "shops")
    public ReturnObject addOnsale(@PathVariable Long shopId,
                                  @PathVariable("id") Long id,
                                  @Validated(NewGroup.class) @RequestBody OnSaleVo vo,
                                  @LoginUser UserDto user){

        if (vo.getBeginTime().isAfter(vo.getEndTime())){
            throw new BusinessException(ReturnNo.LATE_BEGINTIME);
        }

        if (null == vo.getPayTime()){
            vo.setPayTime(vo.getBeginTime());
        }

        if (vo.getPayTime().isBefore(vo.getBeginTime())){
            throw new BusinessException(ReturnNo.ADV_SALE_TIMEEARLY);
        }

        if (vo.getPayTime().isAfter(vo.getEndTime())){
            throw new BusinessException(ReturnNo.ADV_SALE_TIMELATE);
        }

        OnSale onsale = CloneFactory.copy(new OnSale(), vo);

        SimpleOnsaleDto dto = this.transactionTemplate.execute(status -> {
            OnSale newOnsale = this.onsaleService.insert(shopId, id, onsale, user);
            SimpleOnsaleDto simpleOnsaleDto = CloneFactory.copy(new SimpleOnsaleDto(), newOnsale);
            return simpleOnsaleDto;
        });

        return new ReturnObject(ReturnNo.CREATED,dto);
    }

    @GetMapping("/products/{id}/onsales")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getAllOnsale(
            @PathVariable(value = "shopId",required = true) Long shopId,
            @PathVariable(value = "id",required = true) Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<OnSale> onsales = this.onsaleService.retrieveByProductId(shopId, id, page, pageSize);
        List<SimpleOnsaleDto> ret = onsales.stream().map(obj -> CloneFactory.copy(new SimpleOnsaleDto(), obj)).collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(ret, page, pageSize));
    }

    @DeleteMapping("/onsales/{id}")
    @Audit(departName = "shops")
    public ReturnObject delOnsaleId(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @LoginUser UserDto user
    )
    {
        this.onsaleService.tryToDel(shopId, id, user);
        return new ReturnObject();
    }

    @PutMapping("/onsales/{id}/cancel")
    @Audit(departName = "shops")
    public ReturnObject cancelOnsaleId(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @LoginUser UserDto user
    )
    {
        this.onsaleService.cancel(shopId,id,user);
        return new ReturnObject();
    }

    /**
     * 店家查看货品信息详情
     * @param shopId 店铺id
     * @param id 商品id
     * @return
     */
    @GetMapping("products/{id}")
    @Audit(departName = "shops")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getProductId(@PathVariable Long shopId, @PathVariable Long id){
        Product product = this.productService.findProductById(shopId, id);

        return new ReturnObject(new FullProductDto(product));
    }

    /**
     * 店家修改无需审核的货品信息
     * @param shopId 店铺id
     * @param id 商品id
     * @param user 操作者
     * @return
     */
    @PutMapping("products/{id}")
    @Audit(departName = "shops")
    public ReturnObject putProductId(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user,
                                     @RequestBody @Validated ProductVo vo){

        if (vo.getCommissionRatio() != null && shopId != PLATFORM) {
            //修改了commissionratio
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, "平台管理员才可修改");
        }

        Product product = CloneFactory.copy(new Product(), vo);
        this.productService.updateProduct(shopId, id, user, product);
        return new ReturnObject();
    }


    /**
     * 管理员查看运费模板用到的商品
     * @param shopId
     * @param fid
     * @return
     */
    @GetMapping("/templates/{fid}/products")
    @Audit(departName = "shops")
    public ReturnObject getTempalteProduct(@PathVariable Long shopId, @PathVariable Long fid,
                                           @RequestParam(required = false,defaultValue = "1") Integer page,
                                           @RequestParam(required = false,defaultValue = "10") Integer pageSize){
         List<Product> products = this.productService.getTemplateProduct(shopId, fid, page, pageSize);
        List<IdNameTypeDto> collect = products.stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList());
        PageDto<IdNameTypeDto> idNameDtoPageDto = new PageDto<>(collect, page, pageSize);
        return new ReturnObject(idNameDtoPageDto);
    }



    /**
     * 管理员解禁商品
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/products/{id}/allow")
    @Audit(departName = "shops")
    public ReturnObject allowGoods(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user){
        if (!shopId.equals(PLATFORM)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "商品", id, shopId));
        }
        this.productService.allowProduct(id, user);
        return new ReturnObject();
    }

    /**
     * 平台管理员禁售商品
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/products/{id}/prohibit")
    @Audit(departName = "shops")
    public ReturnObject prohibitGoods(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user){
        if (!shopId.equals(PLATFORM)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "商品", id, shopId));
        }
        this.productService.prohibitProduct(id, user);
        return new ReturnObject();
    }



    /**
     * 将两个商品设为相关
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @PostMapping("/products/{id}/relations")
    @Audit(departName = "shops")
    public ReturnObject relateProductId(@PathVariable Long shopId, @PathVariable Long id, @RequestBody @Validated RelateProductVo relateProductVo,
                                        @LoginUser UserDto user){
        this.productService.relateProductId(shopId, id, relateProductVo.getProductId(), user);
        return new ReturnObject();
    }

    /**
     * 将两个商品取消相关
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @DeleteMapping("/products/{id}/relations")
    @Audit(departName = "shops")
    public ReturnObject delRelateProduct(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user){
        this.productService.delRelateProduct(shopId, id, user);
        return new ReturnObject();
    }
}
