//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.dao.LedgerDao;
import cn.edu.xmu.oomall.payment.dao.PayTransDao;
import cn.edu.xmu.oomall.payment.dao.ShopChannelDao;
import cn.edu.xmu.oomall.payment.dao.bo.Ledger;
import cn.edu.xmu.oomall.payment.dao.bo.PayTrans;
import cn.edu.xmu.oomall.payment.dao.bo.ShopChannel;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptor;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import cn.edu.xmu.oomall.payment.dao.channel.dto.PostPayTransAdaptorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.*;
import static cn.edu.xmu.oomall.payment.dao.bo.PayTrans.NEW;
/**
 * @author ych
 * task 2023-dgn1-004
 */

/**
 * 支付的服务
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentService {

    private static  final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private ShopChannelDao shopChannelDao;

    private PayTransDao payTransDao;

    private LedgerDao ledgerDao;


    private PayAdaptorFactory factory;

    @Autowired
    public PaymentService(ShopChannelDao shopChannelDao, PayTransDao payTransDao, LedgerDao ledgerDao, PayAdaptorFactory factory) {
        this.shopChannelDao = shopChannelDao;
        this.payTransDao = payTransDao;
        this.ledgerDao = ledgerDao;
        this.factory = factory;
    }

    /**
     * 创建一个支付交易
     * @param  payTrans 支付交易值对象
     * @param user          当前登录用户
     * @return 支付交易对象
     * @author Ming Qiu
     * <p>
     * date: 2022-11-01 19:18
     */
    public PostPayTransAdaptorDto createPayment(PayTrans payTrans, UserDto user) throws BusinessException {
        ShopChannel shopChannel = this.shopChannelDao.findById(PLATFORM, payTrans.getShopChannelId());
        logger.debug("createPayment: shop = {}", shopChannel);
        return shopChannel.createPayment(payTrans, user);
    }

    /**
     * modified By ych
     * Task 2023-dgn1-004
     * 查询支付交易
     * @param shopId 商铺id
     * @param channelId 支付渠道id
     * @param transNo 支付交易号
     * @param beginTime 交易起时间
     * @param endTime 交易止时间
     * @param status 状态
     * @param page 页
     * @param pageSize 每页数目
     * @return
     */
    public List<PayTrans> retrievePayments(Long shopId, Long channelId, String transNo, LocalDateTime beginTime, LocalDateTime endTime, Integer status, Integer page, Integer pageSize){
        if (PLATFORM.equals(shopId)){//==改为用equals
            return this.payTransDao.retrieveByChannelId(channelId, transNo, status, beginTime, endTime, page, pageSize);
        } else {
            List<Long> shopChannels = this.shopChannelDao.retrieveByChannelId(channelId, 1, MAX_RETURN).stream().map(ShopChannel::getId).collect(Collectors.toList());
            return this.payTransDao.retrieveByShopChannelId(shopChannels, transNo, status, beginTime, endTime, page, pageSize);
        }
    }

    /**
     * 用outNo更新PayTrans对象
     * @param payTrans 更新值
     * @param user 操作用户
     */
    public void updatePaymentByOutNo(PayTrans payTrans, UserDto user){

        Optional<PayTrans> updatePayTrans = this.payTransDao.findByOutNo(payTrans.getOutNo());
        updatePayTrans.ifPresent(o -> {
            payTrans.setId(o.getId());
            this.payTransDao.save(payTrans, SYSTEM);
        });
    }

    /**
     * 分账
     * @param channelId 支付渠道id
     * @param timeEnd 分账截至时间
     * @return
     */
    public void divPayment(Long channelId, LocalDateTime timeEnd){
        Integer channelPage = 1, channelPageSize = MAX_RETURN;
        while (channelPageSize == MAX_RETURN){
            List<ShopChannel> shopChannels = this.shopChannelDao.retrieveByChannelId(channelId, channelPage, channelPageSize);
            channelPageSize = shopChannels.size();
            channelPage += 1;

            for (ShopChannel shopChannel : shopChannels) {
                Integer transPage = 1, transPageSize = MAX_RETURN;
                while (transPageSize == MAX_RETURN) {
                    List<PayTrans> payTransList = this.payTransDao.retrieveSuccessPayTransBySuccessTimeBefore(shopChannel.getId(), timeEnd, transPage, transPageSize);
                    transPage += 1;
                    transPageSize = payTransList.size();

                    for (PayTrans payTrans : payTransList) {
                        payTrans.divide();
                    }
                }
            }
        }
    }


    /**
     * modified By ych
     * task 2023-dgn1-004
     * 查询支付
     * @param shopId 商铺id
     * @param id 支付id
     * @return
     */
    public PayTrans findPayment(Long shopId, Long id){
        PayTrans trans = this.payTransDao.findById(shopId, id);
        if (trans.getStatus().equals(NEW)){
            PayAdaptor payAdaptor = this.factory.createPayAdaptor(trans.getChannel());
            ShopChannel shopChannel = trans.getShopChannel();
            PayTrans queryTrans;
            if (trans.getTransNo() == null){//检测null 不能用 equals
                //删去outNo的判空 outNo不可能为空
                queryTrans = payAdaptor.returnOrderByOutNo(shopChannel, trans.getOutNo());
            }else{
                queryTrans = payAdaptor.returnOrderByTransId(shopChannel, trans.getTransNo());
            }
            trans = check(queryTrans, trans);
        }
        return trans;
    }

    /**
     * 对账
     * @param queryTrans 支付渠道查询获得的信息(值对象)
     * @param trans 数据库里的信息（满血对象）
     * @return 返回的支付交易
     */
    private PayTrans check(PayTrans queryTrans, PayTrans trans) {
        if (!trans.getStatus().equals(NEW)){
            //对账
            if (queryTrans.getAmount().equals(trans.getAmount())){
                queryTrans.setId(trans.getId());
                this.payTransDao.save(queryTrans, SYSTEM);
            }else{
                //错账
                Ledger ledger = new Ledger();
                ledger.setCheckTime(LocalDateTime.now());
                ledger.setAmount(queryTrans.getAmount());
                ledger.setTransNo(queryTrans.getTransNo());
                ledger.setOutNo(queryTrans.getOutNo());
                ledger.setShopChannelId(trans.getShopChannelId());
                Ledger newLedger = this.ledgerDao.insert(ledger, SYSTEM);
                PayTrans updateTrans = new PayTrans();
                updateTrans.setId(trans.getId());
                updateTrans.setLedgerId(newLedger.getId());
                updateTrans.setStatus(PayTrans.WRONG);
                this.payTransDao.save(updateTrans, SYSTEM);
            }
            trans = this.payTransDao.findById(trans.getShopId(), trans.getId());
        }
        return trans;
    }

    /**
     * 取消支付
     * @param shopId 商铺id
     * @param id 支付id
     * @param user 操作者
     */
    public void cancelPayment(Long shopId, Long id, UserDto user){
        PayTrans payTrans = this.payTransDao.findById(shopId, id);
        payTrans.cancel(user);
    }

    public void adjustPayment(Long shopId, Long id, UserDto user) {
        PayTrans payTrans = this.payTransDao.findById(shopId,id);
        payTrans.adjust(user);
    }
}
