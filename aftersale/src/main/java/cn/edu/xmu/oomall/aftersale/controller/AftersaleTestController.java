package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AftersaleTestController {
    private final Logger logger = LoggerFactory.getLogger(AftersaleTestController.class);

    private final AftersaleService aftersaleService;

    @Autowired
    public AftersaleTestController(AftersaleService aftersaleService) {
        this.aftersaleService = aftersaleService;
    }

    @GetMapping("/aftersales/{id}")
    public ReturnObject findAftersaleById(@PathVariable Long id) {
        Aftersale aftersale = this.aftersaleService.findById(id);
        AftersaleDto dto = CloneFactory.copy(new AftersaleDto(), aftersale);
//        dto.setCreator(IdNameTypeDto.builder().id(region.getCreatorId()).name(region.getCreatorName()).build());
//        dto.setModifier(IdNameTypeDto.builder().id(region.getModifierId()).name(region.getModifierName()).build());
        return new ReturnObject(dto);
    }

}
