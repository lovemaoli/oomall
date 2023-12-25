package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicePoMapper extends JpaRepository<ServicePo, Long> {

    List<ServicePo> findByServiceProviderId(Long id);
}
