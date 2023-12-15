//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.product.dao.ProductDao;
import cn.edu.xmu.oomall.product.dao.onsale.OnSaleDao;
import cn.edu.xmu.oomall.product.dao.activity.ActivityDao;
import cn.edu.xmu.oomall.product.dao.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GrouponActService {

    private Logger logger = LoggerFactory.getLogger(GrouponActService.class);

    private RedisUtil redisUtil;

    private ProductDao productDao;

    private OnSaleDao onsaleDao;

    private ActivityDao activityDao;

    @Autowired
    public GrouponActService(RedisUtil redisUtil, OnSaleDao onsaleDao, ActivityDao activityDao, ProductDao productDao) {
        this.redisUtil = redisUtil;
        this.onsaleDao = onsaleDao;
        this.activityDao = activityDao;
        this.productDao = productDao;
    }

    /**
     * 查询团购活动(上线状态)
     *
     * @param shopId    商铺Id
     * @param productId 商品Id
     * @param page      页码
     * @param pageSize  页大小
     * @return
     */
    public List<GrouponAct> retrieveValidByShopIdAndProductId(Long shopId, Long productId, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Integer pageSize) {
        List<GrouponAct> actList = this.activityDao.retrieveValidByShopIdAndProductId(shopId, productId, GrouponAct.ACTCLASS, beginTime, endTime, page, pageSize);
        return actList;
    }

    /**
     * 查看团购详情
     *
     * @param shopId
     * @param id
     * @return
     */
    /**
     * 商户查询特定商铺所有团购活动
     *
     * @param shopId    商铺id
     * @param productId 货品id
     * @param status 状态
     * @param page      页码
     * @param pageSize  每页数目
     * @return 符合条件的预售活动
     */
    public List<GrouponAct> retrieveByShopId(Long shopId, Long productId, Integer status, Integer page,
                                             Integer pageSize) {
        return this.activityDao.retrieveByShopIdAndProductId(shopId, productId, GrouponAct.ACTCLASS, status, page, pageSize);
    }

    public GrouponAct findByShopIdAndActId(Long shopId, Long id) {
        return this.activityDao.findById(id, shopId, GrouponAct.ACTCLASS);
    }

    /**
     * 新增团购活动
     *
     * @param act 团购活动
     * @param onsale 团购对应的销售
     * @param user 操作者
     * @return
     */
    public GrouponAct createGrouponAct(GrouponAct act, OnSale onsale, UserDto user) {
        this.productDao.findNoOnsaleById(onsale.getShopId(), onsale.getProductId());
        OnSale newOnsale = this.onsaleDao.insert(onsale, user);
        GrouponAct newAct = this.activityDao.insert(act, user);
        this.activityDao.insertActivityOnsale(newAct.getId(), newOnsale.getId(), user);
        act.setId(newAct.getId());
        return act;
    }

    /**
     * 将商品增加到团购活动中
     *
     * @param actId 团购活动Id
     * @param onsale 团购对应的销售
     * @param user 操作者
     * @return
     */
    public GrouponAct addToGrouponAct(Long actId, OnSale onsale, UserDto user) {
        GrouponAct act = this.activityDao.findById(actId, onsale.getShopId(), GrouponAct.ACTCLASS);
        this.productDao.findNoOnsaleById(onsale.getShopId(), onsale.getProductId());
        OnSale newOnsale = act.addOnsaleOnAct(onsale, user);    // 向一个活动插入一个新的销售
        this.activityDao.insertActivityOnsale(actId, newOnsale.getId(), user);
        return act;
    }

    /**
     * 修改团购信息
     *
     * @param act 团购活动
     * @param onsale 团购对应的销售
     * @param user 操作者
     * @return
     */
    public void updateGroupon(GrouponAct act, OnSale onsale, UserDto user) {
        GrouponAct oldAct = this.activityDao.findById(act.getId(), act.getShopId(), GrouponAct.ACTCLASS);
        if (onsale.hasValue()) {
            List<OnSale> onsales = oldAct.getOnsaleList();
            for (OnSale obj : onsales) {
                //修改所有对象的beginTime和EndTime
                onsale.setId(obj.getId());
                this.onsaleDao.save(onsale, user);
            }
        }
        this.activityDao.save(act, user);
    }

    /**
     * 将商品从团购活动中移除
     * @param shopId    shop id
     * @param id        activity id
     * @param pid       product id
     * @param user
     *
     * @author WuTong
     * @task 2023-dgn2-008
     */
    public void cancelFromGrouponAct(Long shopId, Long id, Long pid, UserDto user) {
        // 判断活动是否合法
        this.activityDao.findById(id, shopId, GrouponAct.ACTCLASS);
        OnSale onsale = this.onsaleDao.findByActIdEqualsAndProductIdEquals(id, pid);
        String key = onsale.cancel(LocalDateTime.now(), user);
        this.redisUtil.del(key);
    }

    /**
     * 取消团购
     *
     * @param id 团购id
     * @param user 操作者
     * @return
     */
    public void cancel(Long shopId, Long id, UserDto user) {
        GrouponAct activity = this.activityDao.findById(id, shopId, GrouponAct.ACTCLASS);
        List<String> keys = activity.cancel(user);
        this.redisUtil.del(keys.toArray(new String[keys.size()]));
    }

    /**
     * 管理员根据id查询预售信息
     *
     * @param shopId 商铺id
     * @param id     活动id
     * @return 预售活动
     */
    public GrouponAct findById(Long id, Long shopId) {
        return this.activityDao.findById(id, shopId, GrouponAct.ACTCLASS);
    }

    /**
     * 查询活动中的商品
     * @param id 活动id
     * @return 商品
     */
    public List<Product> retrieveProduct(Long id){
        GrouponAct act = this.activityDao.findById(id, PLATFORM, GrouponAct.ACTCLASS);
        List<Product> productList = act.getOnsaleList().stream().map(onsale -> this.productDao.findByOnsale(onsale)).collect(Collectors.toList());
        return productList;
    }

    /**
     * 发布活动
     * @param id 活动id
     * @param user 操作者
     */
    public void publishGroupon(Long id, UserDto user){
        GrouponAct act = this.activityDao.findById(id, PLATFORM, GrouponAct.ACTCLASS);
        GrouponAct updateAct = act.publish();
        this.activityDao.save(updateAct, user);
    }
}
