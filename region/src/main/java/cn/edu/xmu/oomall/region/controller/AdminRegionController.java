//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.region.controller;

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
import cn.edu.xmu.oomall.region.controller.vo.RegionVo;
import cn.edu.xmu.oomall.region.dao.bo.Region;
import cn.edu.xmu.oomall.region.service.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * 地区管理员控制器
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{did}", produces = "application/json;charset=UTF-8")
@Transactional
public class AdminRegionController {
    private final Logger logger = LoggerFactory.getLogger(AdminRegionController.class);
    private final RegionService regionService;

    @Autowired
    public AdminRegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions/{id}/subregions")
    @Audit(departName = "shops")
    public ReturnObject getSubRegionsById(@PathVariable Long did, @PathVariable Long id,
                                                    @RequestParam(required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        List<Region> regionList = this.regionService.retrieveSubRegionsById(id, page, pageSize);
        return new ReturnObject(new PageDto<>(regionList.stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList()), page, pageSize));
    }

    @PostMapping("/regions/{id}/subregions")
    @Audit(departName = "shops")
    public ReturnObject createSubRegions(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user,
                                         @Validated(NewGroup.class) @RequestBody RegionVo vo) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        Region region = CloneFactory.copy(new Region(), vo);
        Region newRegion = this.regionService.createSubRegions(id, region, user);
        IdNameTypeDto dto = IdNameTypeDto.builder().id(newRegion.getId()).name(newRegion.getName()).build();
        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    @PutMapping("/regions/{id}")
    @Audit(departName = "shops")
    public ReturnObject updateRegionById(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user,
                                         @RequestBody @Validated(UpdateGroup.class) RegionVo vo) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        Region region = CloneFactory.copy(new Region(), vo);
        region.setId(id);
        logger.debug("updateRegionById: region = {}", region);
        this.regionService.updateById(region, user);
        return new ReturnObject(ReturnNo.OK);
    }

    @DeleteMapping("/regions/{id}")
    @Audit(departName = "shops")
    public ReturnObject deleteRegionById(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        this.regionService.deleteRegion(id, user);
        return new ReturnObject(ReturnNo.OK);
    }

    @PutMapping("/regions/{id}/suspend")
    @Audit(departName = "shops")
    public ReturnObject suspendRegionById(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        this.regionService.suspendRegion(id, user);
        return new ReturnObject(ReturnNo.OK);
    }

    @PutMapping("/regions/{id}/resume")
    @Audit(departName = "shops")
    public ReturnObject resumeRegionById(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "地区", id, did));
        }
        this.regionService.resumeRegion(id, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // @ExceptionHandler(BusinessException.class)
    // public ResponseEntity<?> exceptionHandler(BusinessException e) {
    //     return new ResponseEntity<>()
    // }
}
