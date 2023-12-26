package cn.edu.xmu.oomall.service.vo;

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getType() {
        return type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

}
