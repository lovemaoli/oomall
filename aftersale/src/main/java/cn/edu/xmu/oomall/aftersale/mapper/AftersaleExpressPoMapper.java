package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersaleExpressPo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AftersaleExpressPoMapper extends JpaRepository<AftersaleExpressPo, Long>{

}
