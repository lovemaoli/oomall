package cn.edu.xmu.oomall.shop.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ShopConfig {

    @Value("${rocketmq.name-server}")
    private String namesrv;

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    /**
     * 配置rocketMQTemplate
     * 避免ProducerGroup组别重复
     *
     * @return
     * @author ZhaoDong Wang
     * 2023-dgn1-009
     */
    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        DefaultMQProducer producer = new TransactionMQProducer();
        int random = new Random().nextInt(100);
        producer.setProducerGroup(String.format("%s-%d", this.producerGroup, random));
        producer.setNamesrvAddr(this.namesrv);
        RocketMQTemplate template = new RocketMQTemplate();
        template.setProducer(producer);
        return template;
    }

}
