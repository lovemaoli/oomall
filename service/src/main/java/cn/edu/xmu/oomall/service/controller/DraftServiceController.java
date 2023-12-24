package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.controller.vo.DraftServiceVo;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import cn.edu.xmu.oomall.service.service.DraftServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class DraftServiceController {
    private final Logger logger = LoggerFactory.getLogger(DraftServiceController.class);

    private final DraftServiceService draftServiceService;

    @Autowired
    public DraftServiceController(DraftServiceService draftServiceService) {
        this.draftServiceService = draftServiceService;
    }

    /**
     * 服务商定义在某个地区为某种商品提供的服务
     * @param mid
     * @param rid
     * @param vo
     * @param user
     * @return
     */
    @GetMapping("/maintainers/{mid}/region/{rid}/service")
    @Audit
    public ReturnObject findDraftServiceById(@PathVariable Long mid, @PathVariable Long rid, @RequestBody DraftServiceVo vo, @LoginUser UserDto user) {
        ReturnObject ret = this.draftServiceService.defServiceForProductInRegion(mid, rid, vo, user);
        return ret;
    }


}
