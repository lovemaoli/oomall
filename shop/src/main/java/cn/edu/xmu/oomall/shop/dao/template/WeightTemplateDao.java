//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.dao.template;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.WeightTemplate;
import cn.edu.xmu.oomall.shop.mapper.po.RegionTemplatePo;
import cn.edu.xmu.oomall.shop.mapper.WeightTemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.po.WeightTemplatePo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WeightTemplateDao implements AbstractTemplateDao {

    private static final Logger logger = LoggerFactory.getLogger(WeightTemplateDao.class);

    private WeightTemplatePoMapper mapper;

    @Autowired
    public WeightTemplateDao(WeightTemplatePoMapper weightTemplatePoMapper) {
        this.mapper = weightTemplatePoMapper;
    }

    @Override
    public RegionTemplate getRegionTemplate(RegionTemplatePo po) throws RuntimeException {
        WeightTemplate bo = CloneFactory.copy(new WeightTemplate(), po);
        Optional<WeightTemplatePo> wPo = this.mapper.findById(new ObjectId(po.getObjectId())) ;
        wPo.ifPresent(templatePo ->{
            CloneFactory.copy(bo,templatePo);
            bo.setObjectId(templatePo.getObjectId().toString());
        });
        return bo;
    }


    @Override
    public void save(RegionTemplate bo) throws RuntimeException{
        WeightTemplatePo po = CloneFactory.copy(new WeightTemplatePo(), bo);
        po.setObjectId(bo.getObjectId());
        this.mapper.save(po);
    }

    @Override
    public void delete(String id) throws RuntimeException{
        this.mapper.deleteById(new ObjectId(id));
    }

    @Override
    public String insert(RegionTemplate bo) throws RuntimeException {
        WeightTemplatePo po = CloneFactory.copy(new WeightTemplatePo(), bo);
        WeightTemplatePo newPo = this.mapper.insert(po);
        return newPo.getObjectId().toString();
    }

}
