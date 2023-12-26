package cn.edu.xmu.oomall.aftersale.dao;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;
import cn.edu.xmu.oomall.aftersale.mapper.OrderItemPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.OrderItemPo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderItemDaoTest {

    @Mock
    private OrderItemPoMapper orderItemPoMapper;

    @InjectMocks
    private OrderItemDao orderItemDao;

    @Test
    void findByIdShouldReturnOrderItemWhenExists() {
        OrderItemPo orderItemPo = new OrderItemPo();
        orderItemPo.setId(1L);

        when(orderItemPoMapper.findById(1L, 1L)).thenReturn(Optional.of(orderItemPo));

        OrderItem result = orderItemDao.findById(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByIdShouldReturnNullWhenDoesNotExist() {
        when(orderItemPoMapper.findById(1L, 1L)).thenReturn(Optional.empty());

        OrderItem result = orderItemDao.findById(1L, 1L);

        assertNull(result);
    }
}