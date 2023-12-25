package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.ExpressPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ExpressPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public class ExpressDao {
    private final static Logger logger = LoggerFactory.getLogger(DraftServiceDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final ExpressPoMapper expressPoMapper;

    public ExpressDao(ExpressPoMapper expressPoMapper) {
        this.expressPoMapper = expressPoMapper;
    }

    public void createExpress(ServiceOrder serviceOrder, UserDto user) {
        ExpressPo po = new ExpressPo();
        po.setService_order_id(serviceOrder.getId());
        expressPoMapper.save(po);
    }
}
