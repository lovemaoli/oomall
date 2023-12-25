package cn.edu.xmu.oomall.service.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceProviderReceiveVo {
    @NotBlank(message = "运单号不能为空", groups = {NewGroup.class})
    private Long billcode;

    public Long getBillcode() {
        return billcode;
    }

    public void setBillcode(Long billcode) {
        this.billcode = billcode;
    }
}
