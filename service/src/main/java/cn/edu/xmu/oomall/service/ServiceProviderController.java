package cn.edu.xmu.oomall.service;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dto.ServiceProviderDto;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.service.ServiceProviderService;
//import cn.edu.xmu.oomall.service.controller.dto.ServiceProviderDto
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ServiceProviderController {
    private final Logger logger = LoggerFactory.getLogger(ServiceProviderController.class);

    private final ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    /**
     * 服务商查看账户信息
     * @param id
     * @return
     */
    @GetMapping("/maintainers/{id}")
    @Audit
    public ReturnObject findServiceProviderById(@PathVariable Long id, @LoginUser UserDto user) {
        ServiceProvider serviceProvider = this.serviceProviderService.findById(id);
        ServiceProviderDto dto = CloneFactory.copy(new ServiceProviderDto(), serviceProvider);
        return new ReturnObject(ReturnNo.OK, dto);
    }

    /**
     * 平台管理员取消服务商
     * @param aid
     * @param mid
     * @param userDto
     * @return
     */
    @GetMapping("/adminusers/{aid}/maintainers/{mid}/cancel")
    @Audit(departName = "shops")
    public ReturnObject cancelServiceProvider(@PathVariable Long aid, @PathVariable Long mid, @LoginUser UserDto userDto) {
        ReturnNo errno = this.serviceProviderService.changeServiceProviderStatus(mid, ServiceProvider.BANNED);
        return new ReturnObject(errno);
    }

    /**
     * 平台管理员恢复服务商
     * @param aid
     * @param mid
     * @param userDto
     * @return
     */
    @GetMapping("/adminusers/{aid}/maintainers/{mid}/resume")
    @Audit(departName = "shops")
    public ReturnObject resumeServiceProvider(@PathVariable Long aid, @PathVariable Long mid, @LoginUser UserDto userDto) {
        ReturnNo errno = this.serviceProviderService.changeServiceProviderStatus(mid, ServiceProvider.VALID);
        return new ReturnObject(errno);
    }

    /**
     * 平台管理员暂停服务商
     * @param aid
     * @param mid
     * @param userDto
     * @return
     */
    @GetMapping("/adminusers/{aid}/maintainers/{mid}/suspend")
    @Audit(departName = "shops")
    public ReturnObject suspendServiceProvider(@PathVariable Long aid, @PathVariable Long mid, @LoginUser UserDto userDto) {
        ReturnNo errno = this.serviceProviderService.changeServiceProviderStatus(mid, ServiceProvider.SUSPEND);
        return new ReturnObject(errno);
    }
}
