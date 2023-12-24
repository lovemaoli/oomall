package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ShopService;
import cn.edu.xmu.oomall.service.mapper.ShopServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RefreshScope
public class ShopServiceDao {

    private final static Logger logger = LoggerFactory.getLogger(ShopServiceDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ShopServicePoMapper shopServicePoMapper;

    public ShopServiceDao(ShopServicePoMapper shopServicePoMapper) {
        this.shopServicePoMapper = shopServicePoMapper;
    }

    public ShopService build(ShopServicePo po){
        return CloneFactory.copy(new ShopService(), po);
    }

    public List<ShopService> findByServiceProviderId(Long id) {
        List<ShopServicePo> shopServices = this.shopServicePoMapper.findByServiceProviderId(id);
        List<ShopService> shopServiceList = new java.util.ArrayList<>(shopServices.size());
        for (ShopServicePo po : shopServices) {
            ShopService shopService = this.build(po);
            shopServiceList.add(shopService);
        }
        return shopServiceList;
    }

    public void save(ShopService shopService) {
        ShopServicePo po = CloneFactory.copy(new ShopServicePo(), shopService);
        this.shopServicePoMapper.save(po);
    }
}
