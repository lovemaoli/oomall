//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.payment.dao.*;
import cn.edu.xmu.oomall.payment.dao.bo.*;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptor;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.*;

/**
 * 退款服务
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RefundService {

    private static  final Logger logger = LoggerFactory.getLogger(RefundService.class);

    private ShopChannelDao shopChannelDao;
    private PayTransDao payTransDao;
    private RefundTransDao refundTransDao;
    private  DivRefundTransDao divRefundTransDao;
    private PayAdaptorFactory factory;

    private LedgerDao ledgerDao;


    /**
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    @Autowired
    public RefundService(ShopChannelDao shopChannelDao, PayTransDao payTransDao, RefundTransDao refundTransDao, DivRefundTransDao divRefundTransDao, PayAdaptorFactory factory, LedgerDao ledgerDao) {
        this.shopChannelDao = shopChannelDao;
        this.payTransDao = payTransDao;
        this.refundTransDao = refundTransDao;
        this.divRefundTransDao = divRefundTransDao;
        this.factory = factory;
        this.ledgerDao = ledgerDao;
    }

    /**
     * 退款
     * @author Ming Qiu
     * date: 2022-11-12 19:58
     * @param shopId 商铺id
     * @param payId 支付id
     * @param amount 退款金额
     * @param divAmount 退款分账金额
     * @param user 操作者
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RefundTrans createRefund(Long shopId, Long payId, Long amount, Long divAmount, UserDto user) {
        //获得支付交易
        PayTrans payTrans = this.payTransDao.findById(shopId, payId);
        logger.debug("createRefund: payTrans = {}",payTrans);
        return payTrans.refund(amount, divAmount, user);
    }

    /**
     * 根据id返回退款对象
     * @author Ming Qiu
     * <p>
     * date: 2022-11-12 21:12
     * @param shopId
     * @param id
     * @return
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    public RefundTrans findRefundById(Long shopId, Long id) {

        RefundTrans trans = this.refundTransDao.findById(shopId, id);
        if (trans.getStatus().equals(RefundTrans.NEW)){
            PayAdaptor payAdaptor = this.factory.createPayAdaptor(trans.getChannel());
            ShopChannel shopChannel = trans.getShopChannel();
            RefundTrans queryTrans;
            if (null == trans.getOutNo()){
                logger.error("findRefundById: RefundTrans (id = {}) outNo is null", id);
                throw new BusinessException(ReturnNo.INCONSISTENT_DATA, String.format("退款交易(id = %d )的tranNo和outNo均为空",  id));
            }
            queryTrans = payAdaptor.returnRefund(shopChannel, trans.getOutNo());

            if (!queryTrans.getStatus().equals(RefundTrans.NEW)){
                //对账
                if (queryTrans.getAmount().equals(trans.getAmount())){
                    queryTrans.setId(id);
                    this.refundTransDao.save(queryTrans, SYSTEM);
                }else{
                    //错账
                    Ledger ledger = new Ledger();
                    ledger.setCheckTime(LocalDateTime.now());
                    ledger.setAmount(queryTrans.getAmount());
                    ledger.setTransNo(queryTrans.getTransNo());
                    ledger.setOutNo(queryTrans.getOutNo());
                    ledger.setShopChannelId(shopChannel.getId());
                    Ledger newLedger = this.ledgerDao.insert(ledger, SYSTEM);
                    RefundTrans updateTrans = new RefundTrans();
                    updateTrans.setId(id);
                    updateTrans.setLedgerId(newLedger.getId());
                    updateTrans.setStatus(PayTrans.WRONG);
                    this.refundTransDao.save(updateTrans, SYSTEM);
                }
                trans = this.refundTransDao.findById(shopId, id);
            }
        }

        return trans;
    }

    /**
     *
     * 查询支付的所有退款单
     * @author Ming Qiu
     * <p>
     * date: 2022-11-12 21:06
     * @param shopId 商铺id
     * @param channelId 支付渠道id
     * @param page 页
     * @param pageSize 每页数目
     * @return 退款交易对象
     * modified By Rui Li
     * task 2023-dgn1-005
     */
    public List<RefundTrans> retrieveRefunds(Long shopId, Long channelId, String transNo, LocalDateTime beginTime, LocalDateTime endTime, Integer statusInteger, Integer page, Integer pageSize) {
        Byte status = null;
        if (null != statusInteger){
            status = statusInteger.byteValue();
        }
        if (PLATFORM == shopId){
            return this.refundTransDao.retrieveByChannelIdAndTransNo(channelId, transNo, beginTime, endTime, status, page, pageSize);
        }else {
            List<Long> shopChannels = this.shopChannelDao.retrieveByChannelId(channelId, 1, MAX_RETURN).stream().map(ShopChannel::getId).collect(Collectors.toList());
            return this.refundTransDao.retrieveByShopChannelIdAndTransNo(shopChannels, transNo, beginTime, endTime, status, page, pageSize);
        }
    }

    /**
     * 调账
     * @author Rui Li
     * task 2023-dgn1-005
     * <p>
     * date: 2023-11-28 17:05
     * @param shopId 商铺id
     * @param id 退款单id
     * @param user 操作者
     * @return
     */
    public void adjustRefund(Long shopId, Long id, UserDto user) {
        //获得退款单
        RefundTrans refundTrans = this.refundTransDao.findById(shopId,id);
        refundTrans.adjust(user);
    }

}
