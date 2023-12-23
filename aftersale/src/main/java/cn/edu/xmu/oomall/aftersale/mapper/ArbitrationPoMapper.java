package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArbitrationPoMapper extends JpaRepository<ArbitrationPo, Long> {

    Optional<ArbitrationPo> findById(Long id);
}
