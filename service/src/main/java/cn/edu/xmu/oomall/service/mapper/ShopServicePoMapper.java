package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ShopServicePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopServicePoMapper extends JpaRepository<ShopServicePo, Long> {

    @Query(value = "select * from service_shop_service where service_provider_id = ?1", nativeQuery = true)
    List<ShopServicePo> findByServiceProviderId(Long id);
}
