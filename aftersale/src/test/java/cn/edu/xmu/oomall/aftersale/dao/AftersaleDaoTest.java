package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AftersaleDaoTest {

    @Mock
    private AftersalePoMapper aftersalePoMapper;

    @InjectMocks
    private AftersaleDao aftersaleDao;

//    @Test
//    void findByIdShouldReturnAftersaleWhenExists() {
//        AftersalePo aftersalePo = new AftersalePo();
//        aftersalePo.setId(1L);
//
//        when(aftersalePoMapper.findById(1L)).thenReturn(Optional.of(aftersalePo));
//
//        Aftersale result = aftersaleDao.findById(1L);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//    }

    @Test
    void findByIdShouldReturnNullWhenDoesNotExist() {
        when(aftersalePoMapper.findById(1L)).thenReturn(Optional.empty());

        Aftersale result = aftersaleDao.findById(1L);

        assertNull(result);
    }

//    @Test
//    void findByBillCodeShouldReturnAftersaleWhenExists() {
//        AftersalePo aftersalePo = new AftersalePo();
//        aftersalePo.setShop_id(1L);
//
//        when(aftersalePoMapper.findPoByBillCode(1234567890L, 1L)).thenReturn(Optional.of(aftersalePo));
//
//        Aftersale result = aftersaleDao.findByBillCode(1234567890L, 1L);
//
//        assertNotNull(result);
//    }

    @Test
    void findByBillCodeShouldReturnNullWhenDoesNotExist() {
        when(aftersalePoMapper.findPoByBillCode(1234567890L, 1L)).thenReturn(Optional.empty());

        Aftersale result = aftersaleDao.findByBillCode(1234567890L, 1L);

        assertNull(result);
    }
}
