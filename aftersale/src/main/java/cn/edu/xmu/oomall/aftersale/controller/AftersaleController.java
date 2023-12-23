package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.util.CloneFactory;
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

    @GetMapping("/aftersales/{id}")
    public ReturnObject findAftersaleById(@PathVariable Long id) {
        Aftersale aftersale = this.aftersaleService.findById(id);
        AftersaleDto dto = CloneFactory.copy(new AftersaleDto(), aftersale);
        return new ReturnObject(dto);
    }

}
