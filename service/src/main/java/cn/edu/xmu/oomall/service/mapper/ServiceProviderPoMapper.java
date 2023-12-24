package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceProviderPoMapper extends JpaRepository<ServiceProviderPo, Long> {

    Optional<ServiceProviderPo> findById(Long id);



}
