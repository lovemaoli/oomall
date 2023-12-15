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
import cn.edu.xmu.javaee.core.validation.UpdateGroup;
import cn.edu.xmu.oomall.product.controller.dto.CategoryDto;
import cn.edu.xmu.oomall.product.controller.vo.CategoryVo;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{shopId}", produces = "application/json;charset=UTF-8")
public class AdminCategoryController {

    private CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获得二级分类
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}/subcategories")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "shops")
    public ReturnObject getSubCategories(@PathVariable("id") Long id,
                                         @PathVariable("shopId") Long shopId)
    {
        if (!PLATFORM.equals(shopId))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, "平台管理员才可以查看分类详细");
        List<Category> categories = this.categoryService.retrieveSubCategories(id);
        List<CategoryDto> ret =  categories.stream().map(category -> new CategoryDto(category)).collect(Collectors.toList());
        return new ReturnObject(ret);
    }

    /**
     * 增加二级分类
     * @param shopId 商铺id
     * @param id 一级分类id
     * @param vo 属性
     * @param creator 操作者
     * @return
     */
    @PostMapping("/categories/{id}/subcategories")
    @Audit(departName = "shops")
    public ReturnObject createSubCategories(@PathVariable("shopId") Long shopId,
                                             @PathVariable("id") Long id,
                                             @Validated(NewGroup.class) @RequestBody CategoryVo vo,
                                             @LoginUser UserDto creator) {
        if (!PLATFORM.equals(shopId))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, "平台管理员才可以修改分类");
        Category category = CloneFactory.copy(new Category(), vo);
        Category newCategory=this.categoryService.createSubCategory(id, category, creator);
        IdNameTypeDto dto=IdNameTypeDto.builder().id(newCategory.getId()).name(newCategory.getName()).build();
        return new ReturnObject(ReturnNo.CREATED,dto);
    }

    /**
     * 管理员修改分类
     * @param shopId 商铺id
     * @param id 分类id
     * @param vo 修改属性
     * @param modifier 操作者
     * @return
     */
    @PutMapping("/categories/{id}")
    @Audit(departName = "shops")
    public ReturnObject updateCategory(@PathVariable("shopId") Long shopId,
                                       @PathVariable("id") Long id,
                                       @Validated(UpdateGroup.class) @RequestBody CategoryVo vo,
                                       @LoginUser UserDto modifier) {
        if (!PLATFORM.equals(shopId))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, "平台管理员才可以修改分类");
        Category category = CloneFactory.copy(new Category(), vo);
        category.setId(id);
        this.categoryService.updateCategory(category, modifier);
        return new ReturnObject();
    }

    @DeleteMapping("/categories/{id}")
    @Audit(departName = "shops")
    public ReturnObject deleteCategory(@PathVariable("shopId") Long shopId,
                                       @PathVariable("id") Long id,
                                       @LoginUser UserDto userDto) {
        if (!shopId.equals(PLATFORM))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, "平台管理员才可以修改分类");
        this.categoryService.deleteCategory(id, userDto);
        return new ReturnObject();
    }
}
