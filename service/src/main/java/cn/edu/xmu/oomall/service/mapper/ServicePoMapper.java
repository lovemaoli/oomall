package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicePoMapper extends JpaRepository<ServicePo, Long> {

    @Query(value = "select * from service_service where service_provider_id = ?1", nativeQuery = true)
    List<ServicePo> findByServiceProviderId(Long id);
}
