package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApplyAftersaleVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ShopConfirmVo;
import cn.edu.xmu.oomall.aftersale.controller.vo.ShopReceiveVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AftersaleController {
    private final Logger logger = LoggerFactory.getLogger(AftersaleController.class);

    private final AftersaleService aftersaleService;

    @Autowired
    public AftersaleController(AftersaleService aftersaleService) {
        this.aftersaleService = aftersaleService;
    }


    /**
     * 管理员或商铺根据售后单id查询售后单信息
     * @param id
     * @return
     */
    @GetMapping("/shops/{shopid}/aftersales/{id}")
    @Audit(departName = "shops")
    public ReturnObject findAftersaleByIdShop(@PathVariable Long shopid, @PathVariable Long id, @LoginUser Long user) {
        Aftersale aftersale = this.aftersaleService.findById(id);
        AftersaleDto dto = CloneFactory.copy(new AftersaleDto(), aftersale);
        return new ReturnObject(dto);
    }

    /**
     * 顾客根据售后单id查询售后单信息
     * @param id
     * @return
     */
    @GetMapping("/aftersales/{id}")
    @Audit
    public ReturnObject findAftersaleById(@PathVariable Long id, @LoginUser Long user) {
        Aftersale aftersale = this.aftersaleService.findById(id);
        AftersaleDto dto = CloneFactory.copy(new AftersaleDto(), aftersale);
        return new ReturnObject(dto);
    }

    /**
     * 顾客提交售后申请
     */
    @PostMapping("/order/{oid}/orderitem/{id}/aftersales")
    @Audit
    public ReturnObject applyAftersale(@PathVariable Long oid, @PathVariable Long id, @RequestBody ApplyAftersaleVo vo, @LoginUser Long user) {
        Aftersale bo = CloneFactory.copy(new Aftersale(), vo);
        ReturnObject ret = aftersaleService.applyAftersale(oid, id, bo, user);
        return ret;
    }

    /**
     * 商铺验收退换货商品
     */
    @PutMapping("/shops/{shopid}/receive")
    @Audit(departName = "shops")
    public ReturnObject shopReceive(@PathVariable Long shopid, @RequestBody ShopReceiveVo vo, @LoginUser Long user) {
        ReturnNo ret = aftersaleService.shopReceive(vo.getBillcode(), shopid, vo.getConfirm(), vo.getConclusion(), user);
        return new ReturnObject(ret);
    }

    /**
     * 商铺审核售后
     */
    @PutMapping("/shops/{shopid}/aftersales/{id}/confirm")
    @Audit(departName = "shops")
    public ReturnObject shopConfirm(@PathVariable Long shopid, @PathVariable Long aid, @RequestBody ShopConfirmVo vo, @LoginUser Long user) {
        ReturnNo ret = aftersaleService.auditAftersale(aid, shopid, vo.getConfirm(), vo.getConclusion(), user);
        return new ReturnObject(ret);
    }

    /**
     * 创建售后服务单
     */
//    @PostMapping("/internal/shops/{shopid}/aftersale/{aid}/serviceOrders")
//    @Audit(departName = "shops")
//    public ReturnObject createAftersaleServiceOrder(@PathVariable Long shopid, @PathVariable Long aid, @RequestBody Object vo, @LoginUser Long user) {
//        return null;
//
//    }

}
