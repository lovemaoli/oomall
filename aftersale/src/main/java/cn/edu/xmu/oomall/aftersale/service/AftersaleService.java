package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.StatusDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AftersaleService {

    private final Logger logger = LoggerFactory.getLogger(AftersaleService.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final AftersaleDao aftersaleDao;
    private final RedisUtil redisUtil;

    @Autowired
    public AftersaleService(AftersaleDao aftersaleDao, RedisUtil redisUtil) {
        this.aftersaleDao = aftersaleDao;
        this.redisUtil = redisUtil;
    }

    public Aftersale findById(Long id) {
        return this.aftersaleDao.findById(id);
    }


}
