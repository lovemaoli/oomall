//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.product.dao.bo.Category;
import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import cn.edu.xmu.oomall.product.dao.bo.Product;
import cn.edu.xmu.oomall.product.dao.onsale.*;
import cn.edu.xmu.oomall.product.dao.openfeign.ShopDao;
import cn.edu.xmu.oomall.product.dao.openfeign.TemplateDao;
import cn.edu.xmu.oomall.product.mapper.jpa.ProductPoMapper;
import cn.edu.xmu.oomall.product.mapper.po.ProductPo;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.*;

/**
 * @author Ming Qiu
 **/
@Repository
@RefreshScope
public class ProductDao {

    private final static Logger logger = LoggerFactory.getLogger(ProductDao.class);

    private final static String KEY = "P%d";

    private final static String OTHER_KEY = "PO%d";

    @Value("${oomall.product.timeout}")
    private int timeout;


    private ProductPoMapper productPoMapper;

    private OnSaleDao onsaleDao;
    private CategoryDao categoryDao;
    private ShopDao shopDao;

    private TemplateDao templateDao;

    private RedisUtil redisUtil;


    @Autowired
    public ProductDao(ProductPoMapper productPoMapper, OnSaleDao onSaleDao, CategoryDao categoryDao, ShopDao shopDao, RedisUtil redisUtil, TemplateDao templateDao) {
        this.productPoMapper = productPoMapper;
        this.onsaleDao = onSaleDao;
        this.redisUtil = redisUtil;
        this.categoryDao = categoryDao;
        this.shopDao = shopDao;
        this.templateDao = templateDao;
    }

    /**
     * 用id查找产品对象，关联有效的onsale对象
     *
     * @param id product Id
     * @return product对象
     */
    public Product findValidById(Long shopId, Long id) throws RuntimeException {
        logger.debug("findValidById: id = {}", id);
        String key = String.format(KEY, id);
        ValidOnSaleProductFactory factory = new ValidOnSaleProductFactory(id);
        Product bo;
        if (this.redisUtil.hasKey(key)) {
            bo = (Product) this.redisUtil.get(key);
            if (shopId != bo.getShopId() && PLATFORM != shopId){
                throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "产品", id, shopId));
            }
            bo = factory.build(bo);
        }else {
            bo = this.findBo(shopId, id, factory);
        }
        return bo;
    }

    /**
     * 用onsaleid查找Product对象
     * 缓存product对象
     *
     * @param  onsale
     * @return product对象
     */
    public Product findByOnsale(OnSale onsale) throws RuntimeException {
        logger.debug("findOnsaleById: id = {}", onsale.getId());
        if (onsale.getId().equals(null)){
            throw new IllegalArgumentException("ProductDao.findProductByOnsaleId: onsaleId can not be null");
        }
        SpecOnSaleProductFactory factory = new SpecOnSaleProductFactory(onsale.getId());
        return this.findBo(PLATFORM, onsale.getProductId(), factory);
    }

    /**
     * 用id查找Product对象（不关联onsale对象）
     * 不缓存
     * @param shopId 商铺id
     * @param id 产品id
     *
     * @return product对象
     */
    public Product findNoOnsaleById(Long shopId, Long id) throws RuntimeException {
        logger.debug("findNoOnsaleById: id = {}", id);
        if (null == id && null == shopId) {
            throw new IllegalArgumentException("ProductDao.findNoOnsaleById: product id can not be null");
        }
        NoOnSaleProductFactory factory = new NoOnSaleProductFactory();
        return this.findBo(shopId, id, factory);
    }

    /**
     * 按照不同的build方法获得bo对象
     *
     * @param shopId 商铺id
     * @param productId 产品id
     * @param factory 不同的getBo函数接口
     * @return Product 对象
     * @author Ming Qiu
     * <p>
     * date: 2022-12-11 7:05
     */
    private Product findBo(Long shopId, Long productId, ProductFactory factory) {
        Optional<ProductPo> ret = this.productPoMapper.findById(productId);
        if (ret.isPresent()) {
            ProductPo po = ret.get();
            if (shopId != po.getShopId() && PLATFORM != shopId){
                throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "产品", productId, shopId));
            }
            String key = String.format(KEY, productId);
            return factory.build(po, Optional.of(key));
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "产品", productId));
        }
    }


    /**
     * 用GoodsPo对象找Goods对象
     *
     * @param name
     * @return Goods对象列表，带关联的Product返回
     */
    public PageDto<Product> retrieveProductByName(String name, int page, int pageSize) throws RuntimeException {
        List<Product> productList = null;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductPo> pageObj = productPoMapper.findByNameEqualsAndStatusNot(name, Product.BANNED, pageable);
        if (!pageObj.isEmpty()) {
            productList = pageObj.stream().map(po -> {
                ValidOnSaleProductFactory factory = new ValidOnSaleProductFactory(po.getId());
                Product bo = factory.build(po, Optional.ofNullable(null));
                return bo;
            }).collect(Collectors.toList());
        } else {
            productList = new ArrayList<>();
        }
        logger.debug("retrieveProductByName: productList = {}", productList);
        return new PageDto<>(productList, page, pageSize);
    }


    /**
     * 用id查找对象
     *
     * @param goodsId goodsId
     * @return product对象
     */
    public List<Product> retrieveOtherProductById(Long shopId, Long goodsId) throws RuntimeException {
        if (null == goodsId) {
            return null;
        }

        String key = String.format(OTHER_KEY, goodsId);
        if (redisUtil.hasKey(key)) {
            List<Long> otherIds = (List<Long>) redisUtil.get(key);
            List<Product> retList = otherIds.stream().map(productId -> this.findValidById(shopId, productId)).filter(obj -> null != obj).collect(Collectors.toList());
            return retList;
        }

        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<ProductPo> ret = productPoMapper.findByGoodsIdEquals(goodsId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Product> retList = ret.stream().map(productPo -> {
                ValidOnSaleProductFactory validOnsaleProductFactory = new ValidOnSaleProductFactory(productPo.getId());
                Product bo = validOnsaleProductFactory.build(productPo, Optional.empty());
                return bo;
            }).collect(Collectors.toList());
            redisUtil.set(key, (ArrayList<Long>) retList.stream().map(obj -> obj.getId()).collect(Collectors.toList()), timeout);
            return retList;
        }
    }

    /**
     * 根据templateId找Product
     * @param shopId 店铺id
     * @param templateId 模板id
     * @return
     */
    public List<Product> findProductByTemplateId(Long shopId, Long templateId) {
        Pageable pageable = PageRequest.of(0, MAX_RETURN);
        Page<ProductPo> ret = this.productPoMapper.findByTemplateIdEquals(templateId, pageable);
        if (ret.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Product> retList = ret.stream().map(productPo -> this.findValidById(shopId, productPo.getId())).collect(Collectors.toList());
            return retList;
        }
    }


    @ToString
    @NoArgsConstructor
    public abstract class ProductFactory{

        public Product build(ProductPo po, Optional<String> redisKey) {
            Product bo = CloneFactory.copy(new Product(), po);
            this.build(bo);
            redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
            return bo;
        }

        public Product build(Product bo) {
            bo.setCategoryDao(categoryDao);
            bo.setProductDao(ProductDao.this);
            bo.setShopDao(shopDao);
            bo.setTemplateDao(templateDao);
            bo.setOnsaleExecutor(this.getExecutor());
            return bo;
        }

        protected abstract OnSaleExecutor getExecutor();
    }

    /**
     * 生产特定历史销售信息Product
     */
    public class SpecOnSaleProductFactory extends ProductFactory {

        private Long onsaleId;

        public SpecOnSaleProductFactory(Long onsaleId) {
            super();
            this.onsaleId = onsaleId;
        }

        @Override
        protected OnSaleExecutor getExecutor() {
            return new SpecOnSaleExecutor(onsaleDao, this.onsaleId);
        }
    }

    /**
     * 生产当前有效销售信息Product
     */
    @ToString(callSuper = true)
    public class ValidOnSaleProductFactory extends ProductFactory {

        private Long productId;

        public ValidOnSaleProductFactory(Long productId) {
            super();
            this.productId = productId;
        }

        @Override
        protected OnSaleExecutor getExecutor() {
            return new ValidOnSaleExecutor(onsaleDao, this.productId);
        }
    }

    /**
     * 生产不附带Onsale的Product
     */
    @ToString(callSuper = true)
    public class NoOnSaleProductFactory extends ProductFactory {

        public NoOnSaleProductFactory() {
            super();
        }

        @Override
        protected OnSaleExecutor getExecutor() {
            return new NoOnSaleExecutor();
        }
    }

    /**
     * 根据店铺id、条形码和名称查找商品
     * 如果非空则and查询
     * @param shopId 商铺id 精确
     * @param barCode 条码 忽略大小写
     * @param name 商品名称 按照开头模糊查询
     * @param page
     * @param pageSize
     * @return
     */
    public List<Product> retrieveByShopIdAndBarCodeAndName(Long shopId, String barCode, String name, Integer page, Integer pageSize) {
        List<Product> ret = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        ProductPo queryExample = new ProductPo();
        if (StringUtils.isNoneBlank(name)){
            queryExample.setName(name);
        }
        if (StringUtils.isNoneBlank(barCode)){
            queryExample.setBarcode(barCode);
        }
        if (null != shopId){
            queryExample.setShopId(shopId);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("barcode", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                .withMatcher("shopId", ExampleMatcher.GenericPropertyMatchers.exact());

        Example example = Example.of(queryExample, matcher);

        Page<ProductPo> pos = this.productPoMapper.findAll(example, pageable);
        ret = pos.stream().map(po -> {
            Long id = po.getId();
            String key = String.format(KEY, id);
            ValidOnSaleProductFactory factory = new ValidOnSaleProductFactory(id);
            Product bo = factory.build(po, Optional.empty());
            return bo;
        }).collect(Collectors.toList());
        return ret;
    }

    /**
     * 插入产品
     *
     * @param product
     * @param user
     * @return
     */
    public Product insert(Product product, UserDto user) {
        product.setGmtCreate(LocalDateTime.now());
        product.setCreator(user);
        ProductPo productPo = CloneFactory.copy(new ProductPo(), product);
        productPo.setId(null);
        if (productPo.getFreeThreshold() == null) {
            productPo.setFreeThreshold(Product.DEFAULT);
        }
        ProductPo save = this.productPoMapper.save(productPo);
        product.setId(save.getId());
        return product;
    }

    /**
     * 更新
     *
     * @param product
     * @param user
     * @return
     * @author wuzhicheng
     */
    public String save(Product product, UserDto user) {
        product.setModifier(user);
        product.setGmtModified(LocalDateTime.now());
        ProductPo productPo = CloneFactory.copy(new ProductPo(), product);
        ProductPo save = this.productPoMapper.save(productPo);
        if (IDNOTEXIST == save.getId()) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商品", product.getId()));
        }
        return String.format(KEY, product.getId());
    }

    /**
     * 根据模板id查询product
     *
     * @param id 模板id
     * @param page
     * @param pageSize
     * @return
     */
    public List<Product> retrieveProductByTemplateId(Long id, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<ProductPo> pos = this.productPoMapper.findByTemplateIdEquals(id, pageable);
        return pos.stream().map(po -> CloneFactory.copy(new Product(), po)).collect(Collectors.toList());
    }

    /**
     * 因为管理员删除产品，将产品改为无分类的产品
     *
     * @param categoryId 商品类目Id
     * @param userDto    操作人
     * @return 影响的product的redis key
     */
    public List<String> changeToNoCategoryProduct(Long categoryId, UserDto userDto) throws RuntimeException {
        logger.debug("changeToNoCategoryProduct: categoryId = {}", categoryId);

        List<ProductPo> pos = this.productPoMapper.findByCategoryIdEquals(categoryId, PageRequest.of(0, MAX_RETURN));
        List<String> keyList = new ArrayList<>(pos.size());
        LocalDateTime now = LocalDateTime.now();
        for (ProductPo po : pos){
            ProductPo updateObj = new ProductPo();
            updateObj.setId(po.getId());
            updateObj.setCategoryId(Category.NOCATEGORY);
            updateObj.setModifierId(userDto.getId());
            updateObj.setGmtModified(now);
            this.productPoMapper.save(updateObj);
            keyList.add(String.format(KEY,po.getId()));
        }
        return keyList;
    }
}
