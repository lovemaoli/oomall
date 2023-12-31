//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.bo;

import cn.edu.xmu.oomall.product.model.strategy.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Order {

    private Long id;

    private List<Item> orderItems;

}
