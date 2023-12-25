package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.dao.bo.AftersaleExpress;
import cn.edu.xmu.oomall.aftersale.mapper.AftersaleExpressPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.OrderItemPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersaleExpressPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public class AftersaleExpressDao {

    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final AftersaleExpressPoMapper aftersaleExpressPoMapper;

    public AftersaleExpressDao(AftersaleExpressPoMapper aftersaleExpressPoMapper) {
        this.aftersaleExpressPoMapper = aftersaleExpressPoMapper;
    }

    public void insert(AftersaleExpress aftersaleExpress) {
        AftersaleExpressPo po = CloneFactory.copy(new AftersaleExpressPo(), aftersaleExpress);
        aftersaleExpressPoMapper.save(po);
    }
}
