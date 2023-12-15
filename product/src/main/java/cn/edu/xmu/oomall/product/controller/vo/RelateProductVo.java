//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * @create 2023-01-01 23:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelateProductVo {
    @NotNull
    private Long productId;
}
