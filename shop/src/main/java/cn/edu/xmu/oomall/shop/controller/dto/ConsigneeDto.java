//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter // 没有get方法没办法序列化
public class ConsigneeDto {
    private String name;
    private String mobile;
    private Long regionId;
    private String address;
}
