package cn.edu.xmu.oomall.aftersale.service;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.service.ArbitrationService;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ArbitrationServiceTest {

    @InjectMocks
    private ArbitrationService arbitrationService;

    @Mock
    private AftersaleDao aftersaleDao;

    @Mock
    private ArbitrationDao arbitrationDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByIdReturnsArbitrationWhenPresent() {
        Arbitration arbitration = new Arbitration();
        when(arbitrationDao.findById(anyLong())).thenReturn(arbitration);

        Arbitration result = arbitrationService.findById(1L);

        assertEquals(arbitration, result);
        verify(arbitrationDao, times(1)).findById(anyLong());
    }

    @Test
    public void applyAftersaleArbitrationReturnsReturnObjectWhenAftersalePresent() {
        Aftersale aftersale = new Aftersale();
        when(aftersaleDao.findById(anyLong())).thenReturn(aftersale);

        UserDto user = new UserDto();
        ReturnObject result = arbitrationService.applyAftersaleArbitration(1L, "reason", user);

        assertEquals(ReturnNo.ARBITRATION_STATE_NOTALLOW, result.getCode());
        verify(aftersaleDao, times(1)).findById(anyLong());
    }

    @Test
    public void changeStatusUpdatesArbitrationStatus() {
        Arbitration arbitration = new Arbitration();

        arbitrationService.changeStatus(arbitration, 1);

        verify(arbitrationDao, times(1)).update(any(Arbitration.class));
    }
}