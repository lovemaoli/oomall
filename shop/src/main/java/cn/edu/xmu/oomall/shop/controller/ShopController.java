//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.controller.dto.*;
import cn.edu.xmu.oomall.shop.controller.vo.*;
import cn.edu.xmu.oomall.shop.dao.bo.Shop;
import cn.edu.xmu.oomall.shop.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.*;

/**
 * 商铺有关API
 */

@RestController
@RequestMapping(value = "/shops", produces = "application/json;charset=UTF-8")
public class ShopController {

    private final Logger logger = LoggerFactory.getLogger(ShopController.class);
    
    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }



    /**
     * 获得店铺的所有状态
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @GetMapping("/states")
    public ReturnObject retrieveShopStates() {
        return new ReturnObject(Shop.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, Shop.STATUSNAMES.get(key))).collect(Collectors.toList()));
    }

    /**
     * 商户申请商铺
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @PostMapping("")
    @Audit(departName = "shops")
    public ReturnObject createShops(@Validated @RequestBody ShopVo shopVo, @LoginUser UserDto user) {
        if(NOSHOP != user.getDepartId() || PLATFORM == user.getDepartId()) {
            throw new BusinessException(ReturnNo.SHOP_USER_HASSHOP, String.format(ReturnNo.SHOP_USER_HASSHOP.getMessage(), user.getId()));
        }
        Shop shop = CloneFactory.copy(new Shop(), shopVo);
        shop.setConsignee(shopVo.getConsignee1().getName());
        shop.setAddress(shopVo.getConsignee1().getAddress());
        shop.setMobile(shopVo.getConsignee1().getMobile());
        shop.setRegionId(shopVo.getConsignee1().getRegionId());
        shop.setFreeThreshold(0); // 由于该字段非空，而创建商户时并不可选（数据库中暂时没实现取一个默认值，因此在这里手动赋值）

        Shop ret = this.shopService.createShop(shop, user);
        IdNameTypeDto dto = IdNameTypeDto.builder().id(ret.getId()).name(ret.getName()).build();
        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    /**
     * 顾客查询商铺信息(只返回上线和下线状态的商铺)
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @GetMapping("")
    public ReturnObject retrieveShops(@RequestParam(required = false) String name,
                                      @RequestParam(required = false,defaultValue = "1") Integer page,
                                      @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        List<Shop> ret = this.shopService.findShopByName(name, page, pageSize);

        return new ReturnObject(new PageDto<>(ret.stream().map(bo -> IdNameTypeDto.builder().id(bo.getId()).name(bo.getName()).build()).collect(Collectors.toList()), page, pageSize));
    }

    /**
     * 商户修改商铺信息
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @PutMapping("/{id}")
    @Audit(departName = "shops")
    public ReturnObject updateShop(@PathVariable("id") Long id,
                                   @Validated @RequestBody ShopModifyVo vo,
                                   @LoginUser UserDto user){
        logger.debug("updateShop: id ={}", id);
        Shop shop = CloneFactory.copy(new Shop(), vo);
        shop.setConsignee(vo.getConsignee1().getName());
        shop.setAddress(vo.getConsignee1().getAddress());
        shop.setMobile(vo.getConsignee1().getMobile());
        shop.setRegionId(vo.getConsignee1().getRegionId());
        shop.setId(id);

        this.shopService.updateShop(shop, user);
        return new ReturnObject();
    }

    /**
     * 平台管理员或商户关闭店铺(只有下线的商铺才能关闭)
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @DeleteMapping("/{id}")
    @Audit(departName = "shops")
    public ReturnObject deleteShop(@PathVariable("id") Long id, @LoginUser UserDto userDto){
        this.shopService.deleteShop(id, userDto);
        return new ReturnObject();
    }

    /**
     * 商户获得商铺信息
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @GetMapping("/{id}")
    @Audit(departName = "shops")
    public ReturnObject findShopById(@PathVariable("id") Long id){
        logger.debug("findShopById: id = {}", id);
        Shop shop = shopService.findShopById(id);
        logger.debug("findShopById: shop = {}", shop);
        return new ReturnObject(new ShopDto(shop));
    }


    /**
     * 平台管理人员查询商铺信息（会返回所有状态的商铺）
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @GetMapping("/{id}/shops")
    @Audit(departName = "shops")
    public ReturnObject retrieveAllShops(@PathVariable("id") Long id, @LoginUser UserDto userDto,
                                         @RequestParam(required = false) Byte status,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false,defaultValue = "1") Integer page,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        if (!PLATFORM.equals(id)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "商铺", id, id));
        }
        List<Shop> shops = this.shopService.findAllShops(status, name, page, pageSize);
       return new ReturnObject(new PageDto<>(shops.stream().map(bo -> IdNameTypeDto.builder().id(bo.getId()).name(bo.getName()).build()).collect(Collectors.toList()), page, pageSize));
    }

    /**
     * 平台管理人员审核商铺信息
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @PutMapping ("/{shopId}/audit")
    @Audit(departName = "shops")
    public ReturnObject updateShopAudit(@PathVariable("shopId") Long shopId, @LoginUser UserDto userDto, @Validated @RequestBody ShopAuditVo shopAuditVo){
        if (!PLATFORM.equals(userDto.getDepartId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "商铺", shopId, shopId));
        }
        this.shopService.updateShopAudit(shopId, userDto, shopAuditVo.getConclusion());
        return new ReturnObject();
    }

    /**
     * 平台管理人员或商户上线商铺
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @PutMapping ("/{id}/online")
    @Audit(departName = "shops")
    public ReturnObject updateShopOnline(@PathVariable("id") Long id, @LoginUser UserDto userDto){
        this.shopService.onlineShop(id, userDto);
        return new ReturnObject();
    }

    /**
     * 平台管理人员或商户下线商铺
     *
     * @author WuTong
     * @task 2023-dgn1-007
     */
    @PutMapping("/{id}/offline")
    @Audit(departName = "shops")
    public ReturnObject updateShopOffline(@PathVariable("id") Long id, @LoginUser UserDto userDto){
        this.shopService.offlineShop(id,userDto);
        return new ReturnObject();
    }
}
