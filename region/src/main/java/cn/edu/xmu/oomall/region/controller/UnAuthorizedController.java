//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.region.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.region.dao.bo.Region;
import cn.edu.xmu.oomall.region.service.RegionService;
import cn.edu.xmu.oomall.region.controller.dto.RegionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 地区控制器
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class UnAuthorizedController {
    private final Logger logger = LoggerFactory.getLogger(UnAuthorizedController.class);
    private final RegionService regionService;

    @Autowired
    public UnAuthorizedController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions/states")
    public ReturnObject retrieveRegionsStates() {
        List<StatusDto> dtoList = this.regionService.retrieveRegionsStates();
        return new ReturnObject(ReturnNo.OK, dtoList);
    }

    @GetMapping("/regions/{id}/subregions")
    public ReturnObject retrieveSubRegionsById(@PathVariable Long id,
                                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<Region> regions = this.regionService.retrieveValidSubRegionsById(id, page, pageSize);
        return new ReturnObject(new PageDto<>(regions.stream().map(bo -> IdNameTypeDto.builder().id(bo.getId()).name(bo.getName()).build()).collect(Collectors.toList()), page, pageSize));
    }

    @GetMapping("/regions/{id}")
    public ReturnObject findRegionById(@PathVariable Long id) {
        Region region = this.regionService.findById(id);
        RegionDto dto = CloneFactory.copy(new RegionDto(), region);
        dto.setCreator(IdNameTypeDto.builder().id(region.getCreatorId()).name(region.getCreatorName()).build());
        dto.setModifier(IdNameTypeDto.builder().id(region.getModifierId()).name(region.getModifierName()).build());
        return new ReturnObject(dto);
    }
}
