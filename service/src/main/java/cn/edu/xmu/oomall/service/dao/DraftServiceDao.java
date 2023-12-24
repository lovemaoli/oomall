package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.service.dao.bo.DraftService;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.DraftServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.ServiceProviderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.DraftServicePo;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
public class DraftServiceDao {

    private final static Logger logger = LoggerFactory.getLogger(DraftServiceDao.class);

    @Value("${oomall.region.timeout}")
    private int timeout;

    private final DraftServicePoMapper draftServicePoMapper;
    private final ServiceProviderPoMapper serviceProviderPoMapper;

    public DraftServiceDao(DraftServicePoMapper draftServicePoMapper, ServiceProviderPoMapper serviceProviderPoMapper) {
        this.draftServicePoMapper = draftServicePoMapper;
        this.serviceProviderPoMapper = serviceProviderPoMapper;
    }

    public DraftService build(DraftServicePo po){
        return CloneFactory.copy(new DraftService(), po);
    }

    public DraftService findById(Long id) {
        Optional<DraftServicePo> optional = this.draftServicePoMapper.findById(id);
        if (optional.isPresent()) {
            return build(optional.get());
        } else {
            return null;
        }
    }

    public DraftService createDraftService(DraftService draftService) {
        DraftServicePo po = CloneFactory.copy(new DraftServicePo(), draftService);
        draftServicePoMapper.save(po);
        return draftService;
    }


}
