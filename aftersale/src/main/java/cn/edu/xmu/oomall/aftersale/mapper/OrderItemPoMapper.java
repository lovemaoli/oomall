package cn.edu.xmu.oomall.aftersale.mapper;


import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderItemPoMapper extends JpaRepository<OrderItemPo, Long> {

    @Query(value = "select * from aftersale_order_item where order_id = ?1 and id = ?2", nativeQuery = true)
    Optional<OrderItemPo> findById(Long orderid, Long orderitemid);
}
