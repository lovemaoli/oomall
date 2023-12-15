package cn.edu.xmu.oomall.shop.controller.dto;

import cn.edu.xmu.oomall.shop.controller.dto.ProductItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreightPriceDto {
    private Long freightPrice;

    private List<List<ProductItemDto>> pack;
}
