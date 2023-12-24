package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopServicePoMapper extends JpaRepository<ShopServicePo, Long> {

    List<ShopServicePo> findByServiceProviderId(Long id);
}
