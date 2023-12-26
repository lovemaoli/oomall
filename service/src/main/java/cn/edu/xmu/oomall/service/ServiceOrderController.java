package cn.edu.xmu.oomall.service;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;

import cn.edu.xmu.oomall.service.vo.ServiceProviderCancelOrderVo;
import cn.edu.xmu.oomall.service.vo.ServiceProviderReceiveVo;
import cn.edu.xmu.oomall.service.service.ServiceOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ServiceOrderController {
    private final Logger logger = LoggerFactory.getLogger(DraftServiceController.class);

    private final ServiceOrderService serviceOrderService;

    @Autowired
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    /**
     * 服务商确认收件
     */
    @PutMapping("/maintainers/{mid}/receive")
    public ReturnObject serviceProviderReceive(@PathVariable Long mid, @RequestBody ServiceProviderReceiveVo vo, @LoginUser UserDto user) {
        ReturnNo code = serviceOrderService.confirmReceive(mid, vo.getBillcode(), user);
        return new ReturnObject(code);
    }

    /**
     * 服务商取消服务单
     */
    @PutMapping("/maintainers/{mid}/serviceorders/{id}/cancelorder")
    public ReturnObject serviceProviderCancelOrder(@PathVariable Long mid, @PathVariable Long id, @RequestBody ServiceProviderCancelOrderVo vo, @LoginUser UserDto user) {
        ReturnNo code = serviceOrderService.serviceProviderCancelOrder(mid, id, vo.getReason(), user);
        return new ReturnObject(code);
    }


}
