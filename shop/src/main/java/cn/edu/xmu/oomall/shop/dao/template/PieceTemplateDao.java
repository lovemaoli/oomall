//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.dao.template;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.dao.bo.template.PieceTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import cn.edu.xmu.oomall.shop.mapper.po.RegionTemplatePo;
import cn.edu.xmu.oomall.shop.mapper.PieceTemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.po.PieceTemplatePo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PieceTemplateDao implements AbstractTemplateDao {

    private static final Logger logger = LoggerFactory.getLogger(PieceTemplateDao.class);

    private PieceTemplatePoMapper mapper;

    @Autowired
    public PieceTemplateDao(PieceTemplatePoMapper pieceTemplatePoMapper) {
        this.mapper = pieceTemplatePoMapper;
    }

    @Override
    public RegionTemplate getRegionTemplate(RegionTemplatePo po){
        PieceTemplate bo = CloneFactory.copy(new PieceTemplate(), po);
        Optional<PieceTemplatePo> wPo = this.mapper.findById(new ObjectId(po.getObjectId())) ;
        logger.debug("getRegionTemplate: wPo = {}, ObjectId = {}", wPo, po.getObjectId());
        wPo.ifPresent(templatePo -> {
            CloneFactory.copy(bo, templatePo);
            bo.setObjectId(templatePo.getObjectId().toString());
            logger.debug("getRegionTemplate: templatePo = {}, bo = {}", templatePo, bo);
        });
        return bo;
    }


    @Override
    public void save(RegionTemplate bo){
        PieceTemplatePo po = CloneFactory.copy(new PieceTemplatePo(), bo);
        po.setObjectId(bo.getObjectId());
        this.mapper.save(po);
    }
    @Override
    public void delete(String id) throws RuntimeException{
        this.mapper.deleteById(new ObjectId(id));
    }
    @Override
    public String insert(RegionTemplate bo){
        PieceTemplatePo po = CloneFactory.copy(new PieceTemplatePo(), bo);
        PieceTemplatePo newPo = this.mapper.insert(po);
        return newPo.getObjectId();
    }
}
