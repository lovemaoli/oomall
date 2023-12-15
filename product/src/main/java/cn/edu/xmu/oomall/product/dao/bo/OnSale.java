//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.product.controller.vo.GrouponActVo;
import cn.edu.xmu.oomall.product.controller.vo.OnSaleVo;
import cn.edu.xmu.oomall.product.dao.ProductDao;
import cn.edu.xmu.oomall.product.dao.activity.ActivityDao;
import cn.edu.xmu.oomall.product.dao.onsale.OnSaleDao;
import cn.edu.xmu.oomall.product.dao.openfeign.ShopDao;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import cn.edu.xmu.oomall.product.mapper.po.OnsalePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({OnsalePo.class, OnSaleVo.class, GrouponActVo.class})
public class OnSale extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(OnSale.class);

    public  final static Long NOTEXIST = -1L;

    @Builder
    public OnSale(Long id, Long creatorId, String creatorName, Long modifierId, String modifierName, LocalDateTime gmtCreate, LocalDateTime gmtModified, Long price, LocalDateTime beginTime, LocalDateTime endTime, Integer quantity, Integer maxQuantity, Long shopId, Long productId, Byte type, Long deposit, LocalDateTime payTime) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.price = price;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
        this.shopId = shopId;
        this.productId = productId;
        this.type = type;
        this.deposit = deposit;
        this.payTime = payTime;
    }

    /**
     * 正常
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte NORMAL = 0;
    /**
     * 秒杀
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte SECONDKILL = 1;

    /**
     * 预售
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte GROUPON = 2;

    /**
     * 预售
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte ADVSALE = 3;


    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    private LocalDateTime beginTime;

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    private LocalDateTime endTime;

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    private Integer maxQuantity;

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }


    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ShopDao shopDao;

    @JsonIgnore
    @ToString.Exclude
    private Shop shop;

    /**
     * @modify Rui Li
     * @task 2023-dgn2-007
     */
    public Shop getShop() {
        if (null == this.shopId){
            return null;
        }
        if (null == this.shop && null != this.shopDao){
            this.shop = this.shopDao.findById(this.shopId);
        }
        return this.shop;
    }

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ProductDao productDao;

    @JsonIgnore
    @ToString.Exclude
    private Product product;

    public Product getProduct() {
        if (null == this.product && null != this.productDao) {
            this.product = this.productDao.findValidById(this.shopId, this.productId);
        }
        return this.product;
    }


    @JsonIgnore
    @ToString.Exclude
    private List<Activity> actList;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ActivityDao activityDao;

    public List<Activity> getActList() {
        if (null == this.actList && null != this.activityDao) {
            this.actList = this.activityDao.retrieveByOnsaleId(this.id);
            logger.debug("getActList: actList = {}", actList);
        }
        logger.debug("getActList: actList = {}", this.actList);
        return this.actList;

    }

    @ToString.Exclude
    @JsonIgnore
    @Setter
    private OnSaleDao onSaleDao;

    /**
     * @modify Rui Li
     * @task 2023-dgn2-007
     * 取消销售
     * @param cancelTime 取消时间
     * @return 取消销售需修改的属性
     */
    public String cancel(LocalDateTime cancelTime, UserDto user){
        OnSale updateOnsale = OnSale.builder().id(this.id).build();

        if(this.getBeginTime().isAfter(cancelTime)){
            //销售活动未开始
            updateOnsale.setEndTime(this.getBeginTime());
        }else if (this.getEndTime().isBefore(cancelTime)) {
            //销售活动已结束
            updateOnsale.setEndTime(this.getEndTime());
        } else{
            //销售活动正在进行中
            updateOnsale.setEndTime(cancelTime);
        }
        return this.onSaleDao.save(updateOnsale, user);
    }

    public Boolean hasValue(){
        return (null != this.productId || null != this.beginTime || null != this.endTime || null != this.price || null != this.shopId ||
                null != this.quantity || null != this.maxQuantity || null != this.deposit || null != this.payTime || null != this.type);


    }

    private Byte type;

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    private Long deposit;

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    private LocalDateTime payTime;

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
