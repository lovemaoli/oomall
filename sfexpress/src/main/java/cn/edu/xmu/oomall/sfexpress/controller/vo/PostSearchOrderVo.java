package cn.edu.xmu.oomall.sfexpress.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * createOrder的msgData
 * @author Zhouzhe Fan
 * dgn3-004-fzz
 */
@NoArgsConstructor
@Data
public class PostSearchOrderVo {

    @JsonProperty("orderId")
    @NotBlank(message = "8013")
    private String orderId;
    @JsonProperty("language")
    private String language = "zh-CN";
}
