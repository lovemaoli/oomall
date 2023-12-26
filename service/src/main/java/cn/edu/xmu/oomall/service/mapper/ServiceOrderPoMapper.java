package cn.edu.xmu.oomall.service.mapper;


import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceOrderPoMapper extends JpaRepository<ServiceOrderPo, Long> {

        @Query(value = "select * from service_order where billcode = ?1 and service_provider_id = ?2", nativeQuery = true)
        Optional<ServiceOrderPo> findByBillcode(Long billcode, Long ServiceProviderId);




}
