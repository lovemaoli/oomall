package cn.edu.xmu.oomall.aftersale.dao;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.mapper.ArbitrationPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArbitrationDaoTest {

    @Mock
    private ArbitrationPoMapper arbitrationPoMapper;

    @InjectMocks
    private ArbitrationDao arbitrationDao;

    @Test
    void findByIdShouldReturnArbitrationWhenExists() {
        Arbitration arbitration = new Arbitration();
        arbitration.setId(1L);

        when(arbitrationPoMapper.findById(1L)).thenReturn(Optional.of(CloneFactory.copy(new ArbitrationPo(), arbitration)));

        Arbitration result = arbitrationDao.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findByIdShouldReturnNullWhenDoesNotExist() {
        when(arbitrationPoMapper.findById(1L)).thenReturn(Optional.empty());

        Arbitration result = arbitrationDao.findById(1L);

        assertNull(result);
    }

    @Test
    void insertShouldSaveArbitration() {
        Arbitration arbitration = new Arbitration();
        arbitration.setId(1L);

        Arbitration result = arbitrationDao.insert(arbitration);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateShouldUpdateArbitration() {
        Arbitration arbitration = new Arbitration();
        arbitration.setId(1L);
        arbitration.setReason("reason");

        Arbitration result = arbitrationDao.update(arbitration);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("reason", result.getReason());
    }

    @Test
    void saveShouldSaveArbitration() {
        Arbitration arbitration = new Arbitration();
        arbitration.setId(1L);

        arbitrationDao.save(arbitration);

        Mockito.verify(arbitrationPoMapper).save(Mockito.any(ArbitrationPo.class));
    }
}
