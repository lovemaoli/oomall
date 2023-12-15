//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.returnval.TwoTuple;
import cn.edu.xmu.oomall.product.dao.CategoryDao;
import cn.edu.xmu.oomall.product.dao.ProductDao;
import cn.edu.xmu.oomall.product.dao.ProductDraftDao;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.dao.bo.ProductDraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 草稿产品
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductDraftService {

    private Logger logger = LoggerFactory.getLogger(ProductDraftService.class);

    private ProductDraftDao productDraftDao;

    private CategoryDao categoryDao;

    private ProductDao productDao;

    private RedisUtil redisUtil;

    public ProductDraftService(ProductDraftDao productDraftDao, CategoryDao categoryDao, ProductDao productDao, RedisUtil redisUtil) {
        this.productDraftDao = productDraftDao;
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.redisUtil = redisUtil;
    }


    /**
     * 商铺管理员申请增加新的Product
     * @param shopId 商铺id
     * @param draft 草稿对象
     * @param user 操作用户
     * @return
     */
    public ProductDraft createDraft(Long shopId, ProductDraft draft, UserDto user) {

        logger.debug("createDraft: shopId = {}, draft = ", shopId, draft);
        Category category = this.categoryDao.findById(draft.getCategoryId());
        if(category.beFirstClassCategory()){
            throw new BusinessException(ReturnNo.CATEGORY_NOTALLOW, String.format(ReturnNo.CATEGORY_NOTALLOW.getMessage(), category.getId()));
        }

        ProductDraft newObj = this.productDraftDao.insert(draft, user);
        return newObj;
    }

    /**
     * 管理员或店家物理删除审核中的Products
     * @author wuzhicheng
     * @param shopId
     * @param id
     * @param user
     */
    public void delDraftProduct(Long shopId, Long id, UserDto user) {
        logger.debug("delProducts: shopId = {}, productId = {}", shopId, id);
        this.productDraftDao.findById(id, shopId);
        this.productDraftDao.delete(id);
    }

    /**
     * 店家或管理员修改审核货品信息
     * @param shopId 商铺id
     * @param productDraft 修改对象
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyById(Long shopId, ProductDraft productDraft, UserDto user) {
        logger.debug("modifyById: shopId = {}, productDraft = {}", shopId, productDraft);
        this.productDraftDao.findById(productDraft.getId(), shopId);
        Category category = this.categoryDao.findById(productDraft.getCategoryId());
        if(category.beFirstClassCategory()){
            throw new BusinessException(ReturnNo.CATEGORY_NOTALLOW, String.format(ReturnNo.CATEGORY_NOTALLOW.getMessage(), productDraft.getCategoryId()));
        }
        this.productDraftDao.save(productDraft, user);
    }

    /**
     * 店家查询草稿商品
     * @param shopId
     * @param page
     * @param pageSize
     * @return
     */
    public List<ProductDraft> getAllProductDraft(Long shopId, Integer page, Integer pageSize) {
        return this.productDraftDao.retrieveProductDraftByShopId(shopId, page, pageSize);
    }

    /**
     * 店家查看草稿货品信息详情
     * @param shopId
     * @param id
     * @return
     */
    public ProductDraft getProductDraft(Long shopId, Long id) {
        ProductDraft draft = this.productDraftDao.findById(id, shopId);
        return draft;
    }

    /**
     * 货品发布
     * @param shopId 商铺id
     * @param id 审核商品id
     * @param commissionRatio 分账比例（可以为空）
     * @param user 操作用户
     * @return 返回商品的id和名称
     */
    public Product publishProduct(Long shopId, Long id, Integer commissionRatio, UserDto user) {
        logger.debug("putGoods: draftProductId = {}", id);
        ProductDraft productDraft = this.productDraftDao.findById(id, shopId);
        TwoTuple<Product, String> retVal = productDraft.publish(commissionRatio, user);
        if (null != retVal.second){
            redisUtil.del(retVal.second);
        }
        return retVal.first;
    }

    /**
     * 店家修改需审核的货品信息
     *
     * @param shopId 商铺id
     * @param id 商品id
     * @param user 操作用户
     * @param draft 修改的属性
     */
    public ProductDraft updateProduct(Long shopId, Long id, UserDto user, ProductDraft draft) {
        logger.debug("updateProduct: productId = {}, draft = {}", id, draft);
        //查询Product,防止修改其他商铺的商品或商品不存在
        this.productDao.findNoOnsaleById(shopId, id);
        draft.setProductId(id);
        return this.createDraft(shopId, draft, user);
    }
}
