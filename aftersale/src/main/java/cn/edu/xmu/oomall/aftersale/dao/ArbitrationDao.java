package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.mapper.ArbitrationPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RefreshScope
public class ArbitrationDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final static String KEY = "AB%d";
    private final ArbitrationPoMapper arbitrationPoMapper;

    public ArbitrationDao(ArbitrationPoMapper arbitrationPoMapper) {
        this.arbitrationPoMapper = arbitrationPoMapper;
    }

    public void build(Arbitration bo){
        bo.setArbitrationDao(this);
    }

    public Arbitration findById(Long id) {
        if (id == null) {
            return null;
        }
        Optional<ArbitrationPo> po = arbitrationPoMapper.findById(id);
        if(po.isPresent()) {
            Arbitration bo = CloneFactory.copy(new Arbitration(), po.get());
            this.build(bo);
            return bo;
        } else {
            return null;
        }
    }

    public Arbitration insert(Arbitration bo) {
        ArbitrationPo po = CloneFactory.copy(new ArbitrationPo(), bo);
        arbitrationPoMapper.save(po);
        return bo;
    }

    public Arbitration update(Arbitration bo) {
        ArbitrationPo po = CloneFactory.copy(new ArbitrationPo(), bo);
        arbitrationPoMapper.save(po);
        return bo;
    }


    public void save(Arbitration arbitration) {
        ArbitrationPo po = CloneFactory.copy(new ArbitrationPo(), arbitration);
        arbitrationPoMapper.save(po);
    }
}
