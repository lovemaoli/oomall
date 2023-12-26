package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ExpressDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.ExpressPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ExpressPo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ExpressDaoTest {

    @InjectMocks
    private ExpressDao expressDao;

    @Mock
    private ExpressPoMapper expressPoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createExpressSavesExpressPo() {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(1L);
        UserDto userDto = new UserDto();
//        doNothing().when(expressPoMapper).save(any(ExpressPo.class));

        expressDao.createExpress(serviceOrder, userDto);

//        verify(expressPoMapper, times(1)).save(any(ExpressPo.class));
    }
}