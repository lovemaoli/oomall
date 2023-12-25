package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
//import cn.edu.xmu.oomall.service.controller.dto.ExpressDto;
//import cn.edu.xmu.oomall.service.controller.vo.ExpressVo;
import cn.edu.xmu.oomall.service.dao.ExpressDao;
import cn.edu.xmu.oomall.service.mapper.po.ExpressPo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ExpressPo.class})
public class Express implements Serializable{
    private Long id;
    private Long service_order_id;
    private Long bill_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getService_order_id() {
        return service_order_id;
    }

    public void setService_order_id(Long service_order_id) {
        this.service_order_id = service_order_id;
    }

    public Long getBill_code() {
        return bill_code;
    }

    public void setBill_code(Long bill_code) {
        this.bill_code = bill_code;
    }
}
