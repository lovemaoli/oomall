package cn.edu.xmu.oomall.service.dao;
import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.ShopService;
import cn.edu.xmu.oomall.service.mapper.ShopServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShopServiceDaoTest {

    @InjectMocks
    private ShopServiceDao shopServiceDao;

    @Mock
    private ShopServicePoMapper shopServicePoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void findByServiceProviderIdReturnsShopServicesWhenPresent() {
//        ShopServicePo shopServicePo = new ShopServicePo();
//        when(shopServicePoMapper.findByServiceProviderId(anyLong())).thenReturn(Arrays.asList(shopServicePo));
//
//        List<ShopService> result = shopServiceDao.findByServiceProviderId(1L);
//
//        assertEquals(1, result.size());
//        verify(shopServicePoMapper, times(1)).findByServiceProviderId(anyLong());
//    }

    @Test
    public void findByServiceProviderIdReturnsEmptyListWhenNotPresent() {
        when(shopServicePoMapper.findByServiceProviderId(anyLong())).thenReturn(Arrays.asList());

        List<ShopService> result = shopServiceDao.findByServiceProviderId(1L);

        assertEquals(0, result.size());
        verify(shopServicePoMapper, times(1)).findByServiceProviderId(anyLong());
    }

//    @Test
//    public void saveCallsSaveOnShopServicePoMapper() {
//        ShopService shopService = new ShopService();
//        doNothing().when(shopServicePoMapper).save(any(ShopServicePo.class));
//
//        shopServiceDao.save(shopService);
//
//        verify(shopServicePoMapper, times(1)).save(any(ShopServicePo.class));
//    }
}