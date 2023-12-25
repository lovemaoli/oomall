package cn.edu.xmu.oomall.service.mapper;


import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOrderPoMapper extends JpaRepository<ServiceOrderPo, Long> {

        @Select("select * from service_order where billcode = #{billcode} and service_provider_id = #{serviceProviderId}")
        Optional<ServiceOrderPo> findByBillcode(Long billcode, Long ServiceProviderId);




}
