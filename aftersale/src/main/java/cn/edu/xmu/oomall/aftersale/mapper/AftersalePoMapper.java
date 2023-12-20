package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface AftersalePoMapper extends JpaRepository<AftersalePo, Long> {

    Optional<AftersalePo> findById(Long id);

}
