package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionPoMapper extends JpaRepository<RegionPo, Long> {

    Optional<RegionPo> findById(Long id);
}
