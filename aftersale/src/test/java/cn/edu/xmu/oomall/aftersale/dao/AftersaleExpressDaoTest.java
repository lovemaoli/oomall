package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.oomall.aftersale.dao.bo.AftersaleExpress;
import cn.edu.xmu.oomall.aftersale.mapper.AftersaleExpressPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersaleExpressPo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class AftersaleExpressDaoTest {

    @Mock
    private AftersaleExpressPoMapper aftersaleExpressPoMapper;

    @InjectMocks
    private AftersaleExpressDao aftersaleExpressDao;

    @Test
    void insertShouldSaveAftersaleExpress() {
        AftersaleExpress aftersaleExpress = new AftersaleExpress();
        aftersaleExpress.setId(1L);

        aftersaleExpressDao.insert(aftersaleExpress);

        verify(aftersaleExpressPoMapper).save(Mockito.any(AftersaleExpressPo.class));
    }
}