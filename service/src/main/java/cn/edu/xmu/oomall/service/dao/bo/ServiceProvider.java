package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.DraftServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.ShopServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
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
@CopyFrom({ServiceProviderPo.class})
public class ServiceProvider implements Serializable{
    @ToString.Exclude
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(ServiceProvider.class);
    @ToString.Exclude
    @JsonIgnore
    public static final Integer APPLY = 0;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer VALID = 1;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer SUSPEND = 2;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer BANNED = 3;
    @ToString.Exclude
    @JsonIgnore
    public static final Integer FAILED = 4;

    public static Map<Integer, String> statusMap = Stream.of(new Object[][] {
                {APPLY, "申请"},
                {VALID, "有效"},
                {SUSPEND, "暂停"},
                {BANNED, "禁用"},
                {FAILED, "申请失败"},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

    public static Map<Integer, List<Integer>> statusTransferMap = Stream.of(new Object[][] {
                {APPLY, Arrays.asList(VALID, FAILED)},
                {VALID, Arrays.asList(SUSPEND)},
                {SUSPEND, Arrays.asList(VALID, BANNED)},
                {BANNED, List.of()},
                {FAILED, List.of()},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (List<Integer>) data[1]));

    public boolean canTransfer(Integer status) {
        return statusTransferMap.get(this.status).contains(status);
    }


    @JsonIgnore
    public String getStatusName() {
        return statusMap.get(this.status);
    }

    private Long id;
    private String name;
    private String consignee;
    private String address;
    private String mobile;
    private Integer service_max_num;
//    private List<Service> services_list;
    private Integer deposit;
    private Integer deposit_threshold;
    private Integer status;

    @JsonIgnore
    @ToString.Exclude
    private ServiceProviderDao serviceProviderDao;

    @JsonIgnore
    @ToString.Exclude
    private DraftServiceDao draftServiceDao;

    @JsonIgnore
    @ToString.Exclude
    private ShopServiceDao shopServiceDao;

    @JsonIgnore
    @ToString.Exclude
    private ServiceDao serviceDao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsignee() { return consignee; }

    public void setConsignee(String consignee) { this.consignee = consignee; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getService_max_num() {
        return service_max_num;
    }

    public void setService_max_num(Integer service_max_num) {
        this.service_max_num = service_max_num;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getDeposit_threshold() {
        return deposit_threshold;
    }

    public void setDeposit_threshold(Integer deposit_threshold) {
        this.deposit_threshold = deposit_threshold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ReturnNo changeStatus(ServiceProviderStatus status) {
        if (canTransfer(status.getValue())) {
            this.status = status.getValue();
            List<ShopService> shopServices = shopServiceDao.findByServiceProviderId(this.id);
            for (ShopService shopService : shopServices) {
                shopService.setStatus(status);
            }
            List<Service> services = serviceDao.findByServiceProviderId(this.id);
            for (Service service : services) {
                if(status == ServiceProviderStatus.APPLY) {
                    service.setStatus(Service.VALID);
                }else{
                    service.setStatus(Service.INVALID);
                }
            }
            return ReturnNo.OK;
        } else {
            return ReturnNo.SERVICE_PROVIDER_STATE_NOTALLOW;
        }
    }

    public ServiceProviderDao getServiceProviderDao() {
        return serviceProviderDao;
    }

    public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
        this.serviceProviderDao = serviceProviderDao;
    }

    public DraftServiceDao getDraftServiceDao() {
        return draftServiceDao;
    }

    public void setDraftServiceDao(DraftServiceDao draftServiceDao) {
        this.draftServiceDao = draftServiceDao;
    }

    public ShopServiceDao getShopServiceDao() {
        return shopServiceDao;
    }

    public void setShopServiceDao(ShopServiceDao shopServiceDao) {
        this.shopServiceDao = shopServiceDao;
    }

    public ServiceDao getServiceDao() {
        return serviceDao;
    }

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public DraftService createDraftService(DraftService bo, UserDto user) {
        if(this.status != ServiceProvider.VALID) {
            logger.debug("createDraftService: 服务商状态不是有效");
            return null;
        }
        bo = draftServiceDao.createDraftService(bo);
        return bo;
    }

}


