//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.product.controller.dto.ProductDto;
import cn.edu.xmu.oomall.product.controller.dto.SimpleProductDto;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.service.CategoryService;
import cn.edu.xmu.oomall.product.service.OnsaleService;
import cn.edu.xmu.oomall.product.service.ProductService;
import cn.edu.xmu.oomall.product.controller.dto.StateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * 商品控制器
 * @author Ming Qiu
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(produces = "application/json;charset=UTF-8")
public class UnAuthorizedController {

    private final Logger logger = LoggerFactory.getLogger(UnAuthorizedController.class);

    private OnsaleService onsaleService;

    private ProductService productService;

    private CategoryService categoryService;

    @Autowired
    public UnAuthorizedController(ProductService productService, CategoryService categoryService,OnsaleService onsaleService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.onsaleService = onsaleService;
    }

    /**
     * 获得商品信息
     * @author Ming Qiu
     * <p>
     * date: 2022-12-04 19:19
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findProductById(@PathVariable("id") Long id) {
        Product product = this.productService.findProductById(PLATFORM, id);
        return new ReturnObject(new ProductDto(product));
    }

    /**
     * 获得商品的所有状态
     * @return
     */
    @GetMapping("/products/states")
    public ReturnObject getStates(){
        return new ReturnObject(Product.STATUSNAMES.keySet().stream().map(code -> StateDto.builder().name(Product.STATUSNAMES.get(code)).code(code).build()).collect(Collectors.toList()));
    }

    /**
     * 查询正式商品
     * @return
     */
    @GetMapping("/products")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getProducts(@RequestParam Long shopId, @RequestParam String barCode, @RequestParam String name,
                                   @RequestParam(required = false,defaultValue = "1") Integer page,
                                   @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        List<Product> prodLists=this.productService.retrieveValidProducts(shopId, barCode, name, page, pageSize);
        List<SimpleProductDto> ret = prodLists.stream()
                .map(o -> new SimpleProductDto(o))
                .collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(ret, page, pageSize));
    }

    /**
     * 获得商品的历史信息
     * @author Ming Qiu
     * <p>
     * date: 2022-12-04 19:19
     * @param id
     * @return
     */
    @GetMapping("/onsales/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findOnsaleById(@PathVariable("id") Long id){
        Product product = this.productService.findByOnsaleId(id);
        ProductDto dto = new ProductDto(product);
        logger.debug("findOnsaleById: dto = {}", dto);
        return new ReturnObject(dto);
    }

    /**
     * 获得二级分类
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}/subcategories")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getSubCategories(@PathVariable("id") Long id) {
        List<Category> categories = this.categoryService.retrieveSubCategories(id);
        return new ReturnObject(categories.stream().map(category -> IdNameTypeDto.builder().id(category.getId()).name(category.getName()).build()).collect(Collectors.toList()));
    }

    /**
     * 查看活动中的商品
     *
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/activities/{id}/onsales")
    public ReturnObject getCouponActProduct(@PathVariable("id") Long id,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<Product> productList = this.onsaleService.getCouponActProduct(id, page, pageSize);
        return new ReturnObject(productList.stream().map(product -> new SimpleProductDto(product)).collect(Collectors.toList()));
    }
}
