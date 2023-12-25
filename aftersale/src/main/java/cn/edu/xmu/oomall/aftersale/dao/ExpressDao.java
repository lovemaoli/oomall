package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
@RefreshScope
public class ExpressDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    public Long createExpress(Long shopid) {
        // TODO 生成随机值
        Random random = new Random();
        return random.nextLong();
    }
}
