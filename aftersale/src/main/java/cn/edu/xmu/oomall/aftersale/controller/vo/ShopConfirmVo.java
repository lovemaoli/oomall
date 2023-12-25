package cn.edu.xmu.oomall.aftersale.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopConfirmVo {
    @NotBlank(message = "验收状态不能为空", groups = {NewGroup.class})
    private Boolean confirm;
    @NotBlank(message = "验收结论不能为空", groups = {NewGroup.class})
    private String conclusion;
    public Boolean getConfirm() {
        return confirm;
    }
    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }
    public String getConclusion() {
        return conclusion;
    }
    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
