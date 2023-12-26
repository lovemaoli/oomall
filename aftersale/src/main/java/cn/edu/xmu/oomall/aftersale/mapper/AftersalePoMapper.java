package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface AftersalePoMapper extends JpaRepository<AftersalePo, Long> {

    Optional<AftersalePo> findById(Long id);

    @Query(value = "SELECT aa.* FROM aftersale_aftersale aa " +
            "INNER JOIN aftersale_aftersale_express aae ON aa.id = aae.aftersale_id " +
            "WHERE aae.billcode = :billcode AND aa.shopid = :shopid", nativeQuery = true)
    Optional<AftersalePo> findPoByBillCode(Long billcode, Long shopid);

}
