package cn.edu.xmu.oomall.shop.service.listener;

import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.oomall.shop.dao.TemplateDao;
import cn.edu.xmu.oomall.shop.dao.bo.template.Template;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import cn.edu.xmu.oomall.shop.mapper.RegionTemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.TemplatePoMapper;
import cn.edu.xmu.oomall.shop.mapper.po.RegionTemplatePo;
import cn.edu.xmu.oomall.shop.mapper.po.TemplatePo;
import cn.edu.xmu.oomall.shop.service.TemplateService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@Component
@RocketMQTransactionListener
public class DelTemplateListener implements RocketMQLocalTransactionListener {

    private TemplateService templateService;

    private TemplateDao templateDao;

    @Autowired
    public DelTemplateListener(TemplateService templateService, TemplateDao templateDao) {
        this.templateService = templateService;
        this.templateDao = templateDao;
    }

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String body = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
        Long templateId = JacksonUtil.parseObject(body, "id", Long.class);
        Long shopId = JacksonUtil.parseObject(body, "shopId", Long.class);

        try {
            this.templateService.deleteTemplate(shopId, templateId);
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String body = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
        Long templateId = JacksonUtil.parseObject(body, "id", Long.class);
        Long shopId = JacksonUtil.parseObject(body, "shopId", Long.class);
        try {
            this.templateDao.findById(shopId, templateId);
        }catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}
