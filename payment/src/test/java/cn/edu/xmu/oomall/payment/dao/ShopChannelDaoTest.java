package cn.edu.xmu.oomall.payment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.PaymentApplication;
import cn.edu.xmu.oomall.payment.dao.bo.Channel;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * 2023-dgn1-006
 * @author huangzian
 */
@SpringBootTest(classes = PaymentApplication.class)
@Transactional(propagation = Propagation.REQUIRED)
public class ShopChannelDaoTest
{
    @MockBean
    RedisUtil redisUtil;
    @Autowired
    private ShopChannelDao shopChannelDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    PayAdaptorFactory payAdaptorFactory;

    /**
     * @author ych
     * task 2023-dgn1-004
     * redis有数据且正常
     */
    @Test
    public void testFindByIdWhenRedisTrueAndSuccess()
    {
        Channel channel=new Channel();
        channel.setId(501L);
        channel.setName("微信支付");

        ShopChannel shopChannel=new ShopChannel();
        shopChannel.setId(501L);
        shopChannel.setShopId(0L);
        shopChannel.setChannelId(501L);
        shopChannel.setChannelDao(channelDao);
        shopChannel.setPayAdaptor(payAdaptorFactory);
        shopChannel.setCreatorName("admin111");
        shopChannel.setSubMchid("1900008XXX");

        Mockito.when(redisUtil.hasKey("SC501")).thenReturn(true);
        Mockito.when(redisUtil.get("SC501")).thenReturn(shopChannel);

        assertEquals(shopChannel.getSubMchid(),shopChannelDao.findById(0L,501L).getSubMchid());
        assertEquals(shopChannel.getCreatorName(),shopChannelDao.findById(0L,501L).getCreatorName());
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * redis有数据但不是自己店铺的
     */
    @Test
    public void testFindByIdWhenRedisTrueButAuthorityWrong()
    {
        Channel channel=new Channel();
        channel.setId(501L);
        channel.setName("微信支付");

        ShopChannel shopChannel=new ShopChannel();
        shopChannel.setId(501L);
        shopChannel.setShopId(1L);
        shopChannel.setCreatorName("admin111");
        shopChannel.setSubMchid("1900008XXX");

        Mockito.when(redisUtil.hasKey("SC501")).thenReturn(true);
        Mockito.when(redisUtil.get("SC501")).thenReturn(shopChannel);

        assertThrows(BusinessException.class,()->shopChannelDao.findById(6L,501L));
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * redis没有数据，数据库拿出来的和自己的商铺号不匹配
     */
    @Test
    public void testFindByIdWhenRedisFalseAndGivenWrongAuthority()
    {
        assertThrows(BusinessException.class,()->shopChannelDao.findById(7L,501L));
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * 数据库找不到对应id
     */
    @Test
    public void testFindByIdGivenWrongId()
    {
        Mockito.when(redisUtil.hasKey("SC501")).thenReturn(false);
        assertThrows(BusinessException.class,()->shopChannelDao.findById(7L,499L));
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * 没有shopId
     */
    @Test
    public void testRetrieveByChannelIdWhenHasNotShopId()
    {
        assertThrows(BusinessException.class,()->shopChannelDao.retrieveByChannelId(null, 1, 10));
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * shopId正确
     */
    @Test
    public void testRetrieveByChannelIdGivenRightShopId()
    {
        List<ShopChannel> shopChannelList = shopChannelDao.retrieveByChannelId(1L, 1, 10);
        assertNotNull(shopChannelList );
    }

    /**
     * @author ych
     * task 2023-dgn1-004
     * shopId不正确
     */
    @Test
    public void testRetrieveByChannelIdGivenWrongShopId()
    {
        List<ShopChannel> shopChannelList = shopChannelDao.retrieveByChannelId(999L, 1, 10);
        assertTrue(shopChannelList.isEmpty());
    }

    /**
     * @author huangzian
     * task 2023-dgn1-006
     * 签约支付渠道，对应的渠道在数据库里已经有了
     */
    @Test
    public void insert()
    {
        UserDto userDto=new UserDto(1L,"admin123",0L,1);

        ShopChannel shopChannel=new ShopChannel();
        shopChannel.setId(501L);
        shopChannel.setShopId(1L);
        shopChannel.setCreatorName("admin111");
        shopChannel.setSubMchid("1900008XXX");
        shopChannel.setChannelDao(channelDao);
        shopChannel.setChannelId(501L);

        assertThrows(BusinessException.class,()->shopChannelDao.insert(shopChannel,userDto));
    }
    /**
     * @author huangzian
     * task 2023-dgn1-006
     * 更新的支付渠道不存在
     */
    @Test
    public void save()
    {
        UserDto userDto=new UserDto(1L,"admin123",0L,1);

        ShopChannel shopChannel=new ShopChannel();
        shopChannel.setId(499L);
        shopChannel.setShopId(1L);
        shopChannel.setCreatorName("admin111");
        shopChannel.setSubMchid("1900008XXX");
        shopChannel.setChannelDao(channelDao);
        shopChannel.setChannelId(501L);

        assertThrows(BusinessException.class,()->shopChannelDao.save(shopChannel,userDto));
    }
    /**
     * @author huangzian
     * task 2023-dgn1-006
     * 删除的不存在
     */
    @Test
    public void delete()
    {
        Long id=499L;
        assertThrows(BusinessException.class,()->shopChannelDao.delete(id));
    }
}
