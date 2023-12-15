//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.controller.dto.SimpleTemplateDto;
import cn.edu.xmu.oomall.shop.controller.dto.TemplateDto;
import cn.edu.xmu.oomall.shop.controller.vo.*;
import cn.edu.xmu.oomall.shop.dao.bo.template.*;
import cn.edu.xmu.oomall.shop.service.RegionTemplateService;
import cn.edu.xmu.oomall.shop.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/shops/{shopId}/templates", produces = "application/json;charset=UTF-8")
public class TemplateController {
    private final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    private TemplateService templateService;

    private RegionTemplateService regionTemplateService;

    @Autowired
    public TemplateController(TemplateService templateService, RegionTemplateService regionTemplateService) {
        this.templateService = templateService;
        this.regionTemplateService = regionTemplateService;
    }

    /**
     * 管理员定义运费模板
     */
    @Audit(departName = "shops")
    @PostMapping("")
    public ReturnObject createTemplate(
            @PathVariable("shopId") Long shopId,
            @Validated @RequestBody TemplateVo vo,
            @LoginUser UserDto user
    ) {
        Template template = CloneFactory.copy(new Template(), vo);
        if (vo.getType().equals(TemplateVo.WEIGHT)){
            template.setTemplateBean(Template.WEIGHT);
        } else if (vo.getType().equals(TemplateVo.PIECE)) {
            template.setTemplateBean(Template.PIECE);
        }

        Template ret = this.templateService.createTemplate(shopId, template, user);
        IdNameTypeDto dto = IdNameTypeDto.builder().id(ret.getId()).name(ret.getName()).build();
        return new ReturnObject(ReturnNo.CREATED, ReturnNo.CREATED.getMessage(), dto);
    }

    /**
     * 获得商品的运费模板
     */
    @Audit(departName = "shops")
    @GetMapping("")
    public ReturnObject retrieveTemplateByName(
            @PathVariable("shopId") Long shopId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        List<Template> templates = this.templateService.retrieveTemplateByName(shopId, name, page, pageSize);
        List<SimpleTemplateDto> dtoList = templates.stream().map(bo -> CloneFactory.copy(new SimpleTemplateDto(), bo)).collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(dtoList, page, pageSize));
    }

    /**
     * 管理员克隆运费模板
     */
    @Audit(departName = "shops")
    @PostMapping("/{id}/clone")
    public ReturnObject cloneTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @LoginUser UserDto user
    ) {
        Template ret = regionTemplateService.cloneTemplate(id, shopId, user);

        IdNameTypeDto dto = IdNameTypeDto.builder().id(ret.getId()).name(ret.getName()).build();

        return new ReturnObject(ReturnNo.CREATED, ReturnNo.CREATED.getMessage(), dto);

    }

    /**
     * 获得运费模板详情
     */
    @Audit(departName = "shops")
    @GetMapping("/{id}")
    public ReturnObject findTemplateById(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id
    ) {

        TemplateDto dto = CloneFactory.copy(new TemplateDto(),templateService.findTemplateById(shopId, id));
        return new ReturnObject(dto);
    }

    /**
     * 管理员修改运费模板
     */
    @Audit(departName = "shops")
    @PutMapping("/{id}")
    public ReturnObject updateTemplateById(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @Validated @RequestBody TemplateVo vo,
            @LoginUser UserDto user
    ) {
        Template template = CloneFactory.copy(new Template(), vo);
        template.setId(id);
        templateService.updateTemplateById(shopId, template, user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 删除运费模板，且同步删除与商品的关系
     */
    @Audit(departName = "shops")
    @DeleteMapping("/{id}")
    public ReturnObject deleteTemplate(
            @LoginUser UserDto user,
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id
    ) {
        this.templateService.deleteTemplate(shopId, id);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 管理员定义重量模板明细
     */
    @Audit(departName = "shops")
    @PostMapping("/{id}/regions/{rid}/weighttemplate")
    public ReturnObject createWeightTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("rid") Long rid,
            @Validated @RequestBody WeightTemplateVo vo,
            @LoginUser UserDto user
    ) {
        WeightTemplate bo = CloneFactory.copy(new WeightTemplate(), vo);
        bo.setTemplateId(id);
        bo.setRegionId(rid);
        this.regionTemplateService.insertRegionTemplate(shopId, bo, user, Weight.class);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /**
     * 管理员修改重量模板明细
     */
    @Audit(departName = "shops")
    @PutMapping("/{id}/regions/{rid}/weighttemplate")
    public ReturnObject updateWeightTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("rid") Long rid,
            @Validated @RequestBody WeightTemplateVo vo,
            @LoginUser UserDto user
    ) {
        WeightTemplate bo = CloneFactory.copy(new WeightTemplate(), vo);
        bo.setTemplateId(id);
        bo.setRegionId(rid);
        this.regionTemplateService.saveRegionTemplate(shopId, bo, user, Weight.class);
        return new ReturnObject();
    }

    /**
     * 管理员删除地区模板
     */
    @Audit(departName = "shops")
    @DeleteMapping("/{id}/regions/{rid}")
    public ReturnObject deleteRegionTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("rid") Long rid
    ) {
        regionTemplateService.deleteRegionTemplate(shopId, id, rid);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 管理员定义件数模板明细
     */
    @Audit(departName = "shops")
    @PostMapping("/{id}/regions/{rid}/piecetemplates")
    public ReturnObject createPieceTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("rid") Long rid,
            @Validated @RequestBody PieceTemplateVo vo,
            @LoginUser UserDto user
    ) {
        PieceTemplate bo = CloneFactory.copy(new PieceTemplate(), vo);
        bo.setTemplateId(id);
        bo.setRegionId(rid);
        this.regionTemplateService.insertRegionTemplate(shopId, bo, user, Piece.class);
        return new ReturnObject(ReturnNo.CREATED);
    }

    /**
     * 管理员修改件数模板
     */
    @Audit(departName = "shops")
    @PutMapping("/{id}/regions/{rid}/piecetemplates")
    public ReturnObject updatePieceTemplate(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @PathVariable("rid") Long rid,
            @Validated @RequestBody PieceTemplateVo vo,
            @LoginUser UserDto user
    ) {
        PieceTemplate bo = CloneFactory.copy(new PieceTemplate(), vo);
        bo.setTemplateId(id);
        bo.setRegionId(rid);
        this.regionTemplateService.saveRegionTemplate(shopId, bo, user, Piece.class);
        return new ReturnObject();
    }

    /**
     * 店家或管理员查询运费模板明细
     */
    @Audit(departName = "shops")
    @GetMapping("/{id}/regions")
    public ReturnObject retrieveRegionTemplateById(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long id,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        /*
         * RegionTemplateVo是PieceTemplateVo和WeightTemplateVo的父类
         * 使用泛型自动映射
         * */
        List<RegionTemplate> ret = this.regionTemplateService.retrieveRegionTemplateById(shopId, id, page, pageSize);
        return new ReturnObject(new PageDto<>(ret, page, pageSize));
    }
}
