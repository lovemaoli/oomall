//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.product.mapper.openfeign.ShopMapper;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDao {

    private ShopMapper shopMapper;

    @Autowired
    public TemplateDao(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public Template findById(Long shopId, Long id){
        InternalReturnObject<Template> ret = this.shopMapper.getTemplateById(shopId, id);
        if (ret.getErrno().equals(ReturnNo.OK)){
            return ret.getData();
        }else{
            throw new BusinessException(ReturnNo.getReturnNoByCode(ret.getErrno()), ret.getErrmsg());
        }
    }
}
