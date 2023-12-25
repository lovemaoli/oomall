package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface AftersalePoMapper extends JpaRepository<AftersalePo, Long> {

    Optional<AftersalePo> findById(Long id);

    @Select("SELECT aa.* FROM aftersale_aftersale aa " +
            "INNER JOIN aftersale_aftersale_express aae ON aa.id = aae.aftersale_id " +
            "WHERE aae.billcode = #{billcode} AND aa.shopid = #{shopid}")
    Optional<AftersalePo> findByBillCode(Long billcode, Long shopid);
}
