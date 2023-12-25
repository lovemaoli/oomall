package cn.edu.xmu.oomall.aftersale.mapper;


import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemPoMapper extends JpaRepository<OrderItemPo, Long> {

    @Select("select * from order_item where order_id = #{orderid} and id = #{orderitemid}")
    Optional<OrderItemPo> findById(Long orderid, Long orderitemid);
}
