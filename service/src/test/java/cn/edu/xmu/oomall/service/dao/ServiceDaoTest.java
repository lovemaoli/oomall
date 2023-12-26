package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.Service;
import cn.edu.xmu.oomall.service.mapper.ServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceDaoTest {

    @InjectMocks
    private ServiceDao serviceDao;

    @Mock
    private ServicePoMapper servicePoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByServiceProviderIdReturnsServicesWhenPresent() {
        ServicePo servicePo = new ServicePo();
        when(servicePoMapper.findByServiceProviderId(anyLong())).thenReturn(Arrays.asList(servicePo));

        List<Service> result = serviceDao.findByServiceProviderId(1L);

        assertEquals(1, result.size());
        verify(servicePoMapper, times(1)).findByServiceProviderId(anyLong());
    }

    @Test
    public void findByServiceProviderIdReturnsEmptyListWhenNotPresent() {
        when(servicePoMapper.findByServiceProviderId(anyLong())).thenReturn(Arrays.asList());

        List<Service> result = serviceDao.findByServiceProviderId(1L);

        assertEquals(0, result.size());
        verify(servicePoMapper, times(1)).findByServiceProviderId(anyLong());
    }

    @Test
    public void saveCallsSaveOnServicePoMapper() {
        Service service = new Service();
        serviceDao.save(service);
    }
}