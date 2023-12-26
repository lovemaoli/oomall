package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.oomall.service.dao.RegionDao;
import cn.edu.xmu.oomall.service.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class RegionDaoTest {

    @InjectMocks
    private RegionDao regionDao;

    @Mock
    private RegionPoMapper regionPoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByIdReturnsRegionPoWhenPresent() {
        RegionPo regionPo = new RegionPo();
        when(regionPoMapper.findById(anyLong())).thenReturn(Optional.of(regionPo));

        RegionPo result = regionDao.findById(1L);

        assertEquals(regionPo, result);
        verify(regionPoMapper, times(1)).findById(anyLong());
    }

    @Test
    public void findByIdReturnsNullWhenNotPresent() {
        when(regionPoMapper.findById(anyLong())).thenReturn(Optional.empty());

        RegionPo result = regionDao.findById(1L);

        assertNull(result);
        verify(regionPoMapper, times(1)).findById(anyLong());
    }
}