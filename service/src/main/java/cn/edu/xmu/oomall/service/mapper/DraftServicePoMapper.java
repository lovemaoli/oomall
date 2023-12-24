package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.DraftServicePo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface DraftServicePoMapper extends JpaRepository<DraftServicePo, Long> {

        Optional<DraftServicePo> findById(Long id);
}
