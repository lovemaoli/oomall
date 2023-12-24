package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.util.CloneFactory;
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

    @GetMapping("/serviceproviders/{id}")
    public ReturnObject findServiceProviderById(@PathVariable Long id) {
        ServiceProvider serviceProvider = this.serviceProviderService.findById(id);
        //ServiceProviderDto dto = CloneFactory.copy(new ServiceProviderDto(), serviceProvider);
        return new ReturnObject(serviceProvider);
    }
}
