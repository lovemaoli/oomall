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
import cn.edu.xmu.javaee.core.util.Common;
import cn.edu.xmu.oomall.product.controller.vo.CouponActVo;
import cn.edu.xmu.oomall.product.dao.bo.CouponAct;
import cn.edu.xmu.oomall.product.service.CouponActService;
import cn.edu.xmu.oomall.product.controller.dto.CouponActDto;
import cn.edu.xmu.oomall.product.controller.dto.SimpleCouponActDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;


/**
 * 优惠控制器
 * @author Liang nan、Fan ninghan
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{shopId}", produces = "application/json;charset=UTF-8")
public class AdminCouponActController {

    private final Logger logger = LoggerFactory.getLogger(AdminCouponActController.class);
    private CouponActService couponActService;

    @Autowired
    public AdminCouponActController(CouponActService couponActService) {

        this.couponActService = couponActService;
    }
    /**
     * 管理员新建己方优惠活动
     *
     * @param shopId
     * @param vo
     * @param creator
     *
     */
    @PostMapping("/couponactivities")
    @Audit(departName = "shops")
    public ReturnObject addCouponactivity(@PathVariable Long shopId,
                                          @Validated @RequestBody CouponActVo vo,
                                          @LoginUser UserDto creator) {
        CouponAct act = CloneFactory.copy(new CouponAct(), vo);
        act.setShopId(shopId);
        act.setActClass(CouponAct.ACTCLASS);
        CouponAct newAct = this.couponActService.addCouponactivity(act, creator);
        return new ReturnObject(ReturnNo.CREATED, CloneFactory.copy(new SimpleCouponActDto(), newAct));
    }

    /**
     * 查看店铺的所有状态优惠活动列表
     *
     * @param shopId
     * @param productId (not required)
     * @param status (not required)
     * @param page (not required)
     * @param pageSize (not required)
     *
     */
    @GetMapping("/couponactivities")
    @Audit(departName = "shops")
    public ReturnObject getCouponactivity(@PathVariable("shopId") Long shopId,
                                          @RequestParam(required = false) Long productId,
                                          @RequestParam(required = false) Integer status,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<CouponAct> acts= this.couponActService.retrieveByShopIdAndProductId(shopId, productId, status, page, pageSize);
        List<SimpleCouponActDto> couponActList = acts.stream()
                .map(couponAct -> CloneFactory.copy(new SimpleCouponActDto(), couponAct)).collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(couponActList, page, pageSize));
    }

    /**
     * 查看优惠活动详情
     *
     * @param shopId
     * @param id
     *
     */
    @GetMapping("/couponactivities/{id}")
    @Audit(departName = "shops")
    public ReturnObject getCouponActDetailed(@PathVariable("shopId") Long shopId,
                                       @PathVariable("id") Long id,
                                       @LoginUser UserDto user) {
        CouponAct act = this.couponActService.findById(shopId, id);
        return new ReturnObject(CloneFactory.copy(new CouponActDto(),act));
    }

    /**
     * 管理员修改己方某优惠活动
     *
     * @param shopId
     * @param id
     * @param couponActVo
     *
     */
    @PutMapping("/couponactivities/{id}")
    @Audit(departName = "shops")
    public ReturnObject putCouponActProduct(@PathVariable("shopId") Long shopId,
                                            @PathVariable("id") Long id,
                                            @Validated @RequestBody CouponActVo couponActVo,
                                            @LoginUser UserDto modifier) {
        CouponAct bo = CloneFactory.copy(new CouponAct(), couponActVo);
        couponActService.updateCouponActivityById(shopId, id, bo, modifier);
        return new ReturnObject();
    }

    /**
     * 管理员取消己方某优惠活动
     * 实现：删除ActivityOnsale表中actId对应的记录
     *
     * @param shopId
     * @param id
     *
     */
    @DeleteMapping("/couponactivities/{id}")
    @Audit(departName = "shops")
    public ReturnObject delCouponAct(@PathVariable("shopId") Long shopId,
                                     @PathVariable("id") Long id,
                                     @LoginUser UserDto modifier) {
        this.couponActService.cancelCouponAct(shopId, id);
        return new ReturnObject();
    }

    /**
     * 管理员审核优惠活动
     *
     * @param shopId
     * @param id
     * @param userDto
     */
    @Audit(departName = "shops")
    @PutMapping("/couponactivities/{id}/publish")
    public ReturnObject publishCouponAct(@PathVariable Long shopId, @PathVariable Long id, @LoginUser UserDto userDto){
        if (PLATFORM != shopId){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, "平台管理员才能审核");
        }
        this.couponActService.publishCouponAct(id,userDto);
        return new ReturnObject();
    }

    /**
     * 管理员将优惠活动加到销售上
     *
     * @param shopId
     * @param id
     * @param sid
     * @param creator
     */
    @PostMapping("/couponactivities/{id}/onsales/{sid}")
    @Audit(departName = "shops")
    public ReturnObject addCouponActToOnsale(@PathVariable Long shopId,
                                             @PathVariable Long id,
                                             @PathVariable Long sid,
                                             @LoginUser UserDto creator) {
        this.couponActService.addCouponActToOnsale(shopId, id, sid, creator);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /**
     * 管理员将销售上的优惠活动取消
     *
     * @author Fan ninghan
     *
     * @param shopId
     * @param id
     * @param sid
     * @param deleter
     *
     */
    @DeleteMapping("/couponactivities/{id}/onsales/{sid}")
    @Audit(departName = "shops")
    public ReturnObject delCouponActFromOnsale(@PathVariable Long shopId,
                                               @PathVariable Long id,
                                               @PathVariable Long sid,
                                               @LoginUser UserDto deleter) {
        this.couponActService.delCouponActFromOnsale(shopId, id, sid, deleter);
        return new ReturnObject();
    }
}
