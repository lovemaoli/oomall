package cn.edu.xmu.oomall.service.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DraftServiceVo {
    @NotBlank(message = "服务名不能为空", groups = {NewGroup.class})
    private String name;

    @NotBlank(message = "服务描述不能为空", groups = {NewGroup.class})
    private String description;

    @NotBlank(message = "服务类型不能为空", groups = {NewGroup.class})
    private Integer type;

    @NotBlank(message = "服务商品类别不能为空", groups = {NewGroup.class})
    private String category_name;

}
