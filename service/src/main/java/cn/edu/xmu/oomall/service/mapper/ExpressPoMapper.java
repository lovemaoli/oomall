package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ExpressPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ExpressPoMapper extends JpaRepository<ExpressPo, Long> {


}
