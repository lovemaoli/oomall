package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.mapper.RegionPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.RegionPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public class RegionDao {
    private final static Logger logger = LoggerFactory.getLogger(ServiceProviderDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final RegionPoMapper regionPoMapper;

    public RegionDao(RegionPoMapper regionPoMapper) {
        this.regionPoMapper = regionPoMapper;
    }

    public RegionPo findById(Long id) {
        return regionPoMapper.findById(id).orElse(null);
    }

}
