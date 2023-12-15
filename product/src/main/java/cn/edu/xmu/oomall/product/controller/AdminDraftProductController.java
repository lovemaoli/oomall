//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.javaee.core.validation.UpdateGroup;
import cn.edu.xmu.oomall.product.controller.dto.ProductDraftDto;
import cn.edu.xmu.oomall.product.controller.dto.SimpleProductDraftDto;
import cn.edu.xmu.oomall.product.controller.vo.CommissionRatioVo;
import cn.edu.xmu.oomall.product.controller.vo.ProductDraftVo;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.dao.bo.ProductDraft;
import cn.edu.xmu.oomall.product.service.ProductDraftService;
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
 * 商品申请的控制器
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{shopId}", produces = "application/json;charset=UTF-8")
public class AdminDraftProductController {

    private TransactionTemplate transactionTemplate;

    private ProductDraftService productDraftService;

    @Autowired
    public AdminDraftProductController(TransactionTemplate transactionTemplate, ProductDraftService productDraftService) {
        this.transactionTemplate = transactionTemplate;
        this.productDraftService = productDraftService;
    }

    /**
     * 商铺管理员申请增加新的Product
     * @param shopId
     * @param user
     * @return
     */
    @PostMapping("/draftproducts")
    @Audit(departName = "shops")
    public ReturnObject createDraft(@PathVariable Long shopId,
                                    @RequestBody @Validated(NewGroup.class) ProductDraftVo vo,
                                    @LoginUser UserDto user){

        ProductDraft productDraft = CloneFactory.copy(new ProductDraft(), vo);

        //显式定义事务的边界，带返回值
        //因为draft的get的方法可能触发数据库操作，索引放到事务中
        SimpleProductDraftDto dto = this.transactionTemplate.execute(status -> {
            ProductDraft draft =this.productDraftService.createDraft(shopId, productDraft, user);
            return CloneFactory.copy(new SimpleProductDraftDto(), draft);
        });

        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    /**
     * 管理员或店家物理删除审核中的Products
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @DeleteMapping("/draftproducts/{id}")
    @Audit(departName = "shops")
    public ReturnObject delProducts(@PathVariable Long shopId, @PathVariable Long id,
                                    @LoginUser UserDto user){
        this.productDraftService.delDraftProduct(shopId, id, user);
        return new ReturnObject();
    }

    /**
     * 管理员或店家修改审核中的Products
     * @author wuzhicheng
     * @param shopId
     * @param id
     * @param user
     * @param vo
     * @return
     */
    @PutMapping("/draftproducts/{id}")
    @Audit(departName = "shops")
    public ReturnObject modifyDraft(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user,
                                    @Validated(UpdateGroup.class) @RequestBody ProductDraftVo vo){
        ProductDraft draft = CloneFactory.copy(new ProductDraft(), vo);
        draft.setId(id);
        this.productDraftService.modifyById(shopId, draft, user);
        return new ReturnObject();
    }

    /**
     * 店家查看草稿商品
     * @param shopId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/draftproducts")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "shops")
    public ReturnObject getAllProductDraft(@PathVariable Long shopId,
                                                             @RequestParam(required = false,defaultValue = "1") Integer page,
                                                             @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        List<ProductDraft> allProductDraft = this.productDraftService.getAllProductDraft(shopId, page, pageSize);
        List<SimpleProductDraftDto> collect = allProductDraft.stream().map(o -> new SimpleProductDraftDto(o)).collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(collect, page, pageSize));
    }

    /**
     * 店家查看草稿商品详情
     * @param shopId
     * @param id
     * @return
     */
    @GetMapping("/draftproducts/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "shops")
    public ReturnObject getProductDraft(@PathVariable Long shopId, @PathVariable Long id){
        ProductDraft draft = this.productDraftService.getProductDraft(shopId, id);
        return new ReturnObject(new ProductDraftDto(draft));
    }

    /**
     * 货品发布
     * @param shopId
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/draftproducts/{id}/publish")
    @Audit(departName = "shops")
    public ReturnObject publishProduct(@PathVariable Long shopId, @PathVariable Long id, @RequestBody CommissionRatioVo vo, @LoginUser UserDto user){

        if(PLATFORM != user.getId()){
            //只有平台管理员能发布商品
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "上架商品", id, shopId));
        }

        Integer commissionRatio = vo.getCommissionRatio();
        Product product = this.productDraftService.publishProduct(shopId, id, commissionRatio, user);
        IdNameTypeDto dto = IdNameTypeDto.builder().id(product.getId()).name(product.getName()).build();
        return new ReturnObject(dto);
    }

    /**
     * 店家修改需审核的货品信息
     * @param shopId 店铺id
     * @param id 商品id
     * @param user 操作者
     * @return
     */
    @PutMapping("products/{id}/apply")
    @Audit(departName = "shops")
    public ReturnObject putProductId(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto user,
                                     @RequestBody @Validated(UpdateGroup.class) ProductDraftVo vo){

        ProductDraft draft = CloneFactory.copy(new ProductDraft(), vo);
        //显式定义事务的边界，带返回值
        //因为draft的get的方法可能触发数据库操作，索引放到事务中
        SimpleProductDraftDto dto = this.transactionTemplate.execute(status -> {
            ProductDraft newObj =this.productDraftService.updateProduct(shopId, id, user, draft);
            return CloneFactory.copy(new SimpleProductDraftDto(), newObj);
        });

        return new ReturnObject(dto);
    }
}
