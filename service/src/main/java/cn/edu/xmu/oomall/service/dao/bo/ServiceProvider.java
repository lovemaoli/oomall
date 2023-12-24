package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
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
//@CopyFrom({AftersalePo.class, AftersaleVo.class})
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
    private String address;
    private String mobile;
    private Integer service_max_num;
//    private List<Service> services_list;
    private Integer deposit;
    private Integer deposit_threshold;
    private Integer status;

    private ServiceProviderDao serviceProviderDao;

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
        if (canTransfer(status)) {
            this.status = status;
        } else {
            throw new BusinessException(ReturnNo.SERVICE_PROVIDER_STATE_NOTALLOW);
        }
    }

    public ReturnNo changeStatus(Integer status) {
        if (canTransfer(status)) {
            this.status = status;
            // loop...
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
}


