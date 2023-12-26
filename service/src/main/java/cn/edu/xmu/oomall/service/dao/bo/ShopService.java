package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.rocketmq.common.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ShopServicePo.class})
public class ShopService implements Serializable{
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer VALID = 100;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SHOPSUSPEND = 201;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATSUSPENDSUSPEND = 202;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATSUSPENDVALID = 203;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SHOPCANCEL = 301;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer PLATCANCEL = 302;

    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
            {VALID, "有效"},
            {SHOPSUSPEND, "商户暂停"},
            {PLATSUSPENDSUSPEND, "平台暂停商户暂停"},
            {PLATSUSPENDVALID, "平台暂停"},
            {SHOPCANCEL, "商户取消"},
            {PLATCANCEL,"平台取消"}
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
            {VALID, Arrays.asList(SHOPSUSPEND, PLATSUSPENDVALID)},
            {SHOPSUSPEND, Arrays.asList(VALID,PLATSUSPENDSUSPEND,SHOPCANCEL)},
            {PLATSUSPENDSUSPEND, Arrays.asList(SHOPSUSPEND, PLATCANCEL)},
            {PLATSUSPENDVALID, Arrays.asList(VALID, PLATCANCEL)},
            {SHOPCANCEL,List.of()},
            {PLATCANCEL, List.of()},
    }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public static Map<Pair<Integer, ServiceProviderStatus>, Integer> providerStatusTransferMap = Stream.of(new Object[][] {
            {new Pair<>(VALID, ServiceProviderStatus.SUSPEND), PLATSUSPENDVALID},
            {new Pair<>(PLATSUSPENDVALID, ServiceProviderStatus.VALID), VALID},
            {new Pair<>(SHOPSUSPEND, ServiceProviderStatus.SUSPEND), PLATSUSPENDSUSPEND},
            {new Pair<>(PLATSUSPENDSUSPEND, ServiceProviderStatus.VALID), SHOPSUSPEND},
            {new Pair<>(PLATSUSPENDVALID, ServiceProviderStatus.BANNED), PLATCANCEL},
            {new Pair<>(PLATSUSPENDSUSPEND, ServiceProviderStatus.BANNED), PLATCANCEL}
    }).collect(Collectors.toMap(data -> (Pair<Integer, ServiceProviderStatus>) data[0], data -> (Integer) data[1]));
    public boolean canTransfer(Integer status) {
        return statusTransferMap.get(this.shop_status).contains(status);
    }

    @JsonIgnore
    public String getStatusName() {
        return statusMap.get(this.shop_status);
    }

    private Long id;
    private String name;
    private Long shop_id;
    private Long service_id;
    private LocalDateTime create_time;
    private Integer shop_status;
    private Long product_id;
    private Long service_provider_id;


    @JsonIgnore
    @ToString.Exclude
    private ShopServiceDao shopServiceDao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public LocalDateTime getCreate_time() { return create_time;}

    public void setCreate_time(LocalDateTime create_time) { this.create_time = create_time; }

    public Long getService_provider_id() {
        return service_provider_id;
    }

    public void setService_provider_id(Long service_provider_id) {
        this.service_provider_id = service_provider_id;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getShop_status() {
        return shop_status;
    }

    public void setShop_status(Integer shop_status) {
        if(canTransfer(shop_status)){
            this.shop_status = shop_status;
        } else {
          throw new BusinessException(ReturnNo.SHOP_SERVICE_STATE_NOTALLOW);
        }
    }

    public void setStatus(ServiceProviderStatus status) {
        Pair<Integer, ServiceProviderStatus> pair = new Pair<>(this.shop_status, status);
        if (providerStatusTransferMap.containsKey(pair)) {
            this.shop_status = providerStatusTransferMap.get(pair);
            shopServiceDao.save(this);
            logger.debug("ShopService: " + this.id + " status changed to " + this.shop_status);
        }else{
            logger.debug("ShopService: " + this.id + " status not changed");
        }
    }

    public ReturnNo changeStatus(Integer shop_status) {
        if (canTransfer(shop_status)) {
            this.shop_status = shop_status;
            // loop...
            return ReturnNo.OK;
        } else {
            return ReturnNo.SHOP_SERVICE_STATE_NOTALLOW;
        }
    }

    public ShopServiceDao getShopServiceDao() {
        return shopServiceDao;
    }

    public void setShopServiceDao(ShopServiceDao shopServiceDao) {
        this.shopServiceDao = shopServiceDao;
    }
}
