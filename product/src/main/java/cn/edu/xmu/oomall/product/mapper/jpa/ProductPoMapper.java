//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.mapper.jpa;

import cn.edu.xmu.oomall.product.mapper.po.ProductPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductPoMapper extends JpaRepository<ProductPo, Long> {

    Page<ProductPo> findByNameEqualsAndStatusNot(String name, Byte status, Pageable pageable);

    Page<ProductPo> findByGoodsIdEquals(Long id, Pageable pageable);

    Page<ProductPo> findByTemplateIdEquals(Long templateId, Pageable pageable);

    List<ProductPo> findByCategoryIdEquals(Long id, Pageable pageable);

}
