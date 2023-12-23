package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationRequestVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.arbitration.Arbitration;
import cn.edu.xmu.oomall.aftersale.service.ArbitrationService;
import cn.edu.xmu.oomall.aftersale.controller.dto.ArbitrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ArbitrationController {
    private final Logger logger = LoggerFactory.getLogger(AftersaleController.class);

    private final ArbitrationService arbitrationService;

    public ArbitrationController(ArbitrationService arbitrationService) {
        this.arbitrationService = arbitrationService;
    }

    /**
     * 顾客提交仲裁
     * @param aid
     * @param user
     * @return
     */
    @PostMapping("/aftersales/{aid}/arbitrations")
    @Audit
    public ReturnObject createArbitration(@PathVariable Long aid, @RequestBody ArbitrationRequestVo requestVo, @LoginUser UserDto user) {
        ArbitrationDto dto = arbitrationService.applyAftersaleArbitration(aid, requestVo.getReason(), user);
        return new ReturnObject(dto);
    }

    /**
     * 顾客取消仲裁
     * @param aid
     * @return
     */
    @DeleteMapping("/arbitrations/{aid}")
    @Audit
    public ReturnObject deleteArbitration(@PathVariable Long aid, @LoginUser UserDto user) {
        Arbitration arbitration = this.arbitrationService.findById(aid);
        ReturnNo code = arbitrationService.cancelArbitration(aid, user);
        return new ReturnObject(code);
    }
}
