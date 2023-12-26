package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import cn.edu.xmu.oomall.service.mapper.DraftServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.DraftServicePo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class DraftServiceDaoTest {

    @InjectMocks
    private DraftServiceDao draftServiceDao;

    @Mock
    private DraftServicePoMapper draftServicePoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void findByIdReturnsDraftServiceWhenPresent() {
//        DraftServicePo draftServicePo = new DraftServicePo();
//        when(draftServicePoMapper.findById(anyLong())).thenReturn(Optional.of(draftServicePo));
//
//        DraftService result = draftServiceDao.findById(1L);
//
//        assertEquals(draftServicePo, result);
//        verify(draftServicePoMapper, times(1)).findById(anyLong());
//    }

    @Test
    public void findByIdReturnsNullWhenNotPresent() {
        when(draftServicePoMapper.findById(anyLong())).thenReturn(Optional.empty());

        DraftService result = draftServiceDao.findById(1L);

        assertNull(result);
        verify(draftServicePoMapper, times(1)).findById(anyLong());
    }

    @Test
    public void createDraftServiceReturnsDraftService() {
        DraftService draftService = new DraftService();
        DraftServicePo draftServicePo = new DraftServicePo();
        when(draftServicePoMapper.save(any())).thenReturn(draftServicePo);

        DraftService result = draftServiceDao.createDraftService(draftService);

        assertEquals(draftService, result);
        verify(draftServicePoMapper, times(1)).save(any());
    }
}
