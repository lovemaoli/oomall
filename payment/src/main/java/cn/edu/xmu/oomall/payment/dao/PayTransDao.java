//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.payment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.payment.dao.bo.PayTrans;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.payment.dao.bo.Transaction;
import cn.edu.xmu.oomall.payment.dao.channel.PayAdaptorFactory;
import cn.edu.xmu.oomall.payment.mapper.generator.po.PayTransPo;
import cn.edu.xmu.oomall.payment.mapper.generator.po.PayTransPoExample;
import cn.edu.xmu.oomall.payment.mapper.manual.PayTransPoManualExample;
import cn.edu.xmu.oomall.payment.mapper.manual.PayTransPoManualMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@Repository
public class PayTransDao extends TransactionDao {

    private static final Logger logger = LoggerFactory.getLogger(PayTransDao.class);

    private ShopChannelDao shopChannelDao;

    private RefundTransDao refundTransDao;
    private DivPayTransDao divPayTransDao;

    private cn.edu.xmu.oomall.payment.mapper.generator.PayTransPoMapper payTransPoMapper;

    private PayTransPoManualMapper payTransPoManualMapper;

    private PayAdaptorFactory factory;

    @Autowired
    @Lazy
    public PayTransDao(ShopChannelDao shopChannelDao, RefundTransDao refundTransDao, DivPayTransDao divPayTransDao, cn.edu.xmu.oomall.payment.mapper.generator.PayTransPoMapper payTransPoMapper, PayTransPoManualMapper payTransPoManualMapper, PayAdaptorFactory factory) {
        this.shopChannelDao = shopChannelDao;
        this.refundTransDao = refundTransDao;
        this.divPayTransDao = divPayTransDao;
        this.payTransPoMapper = payTransPoMapper;
        this.payTransPoManualMapper = payTransPoManualMapper;
        this.factory = factory;
    }


    private PayTrans build(PayTransPo po) {
        PayTrans ret;
        ret = CloneFactory.copy(new PayTrans(), po);
        ret.setRefundTransDao(this.refundTransDao);
        ret.setShopChannelDao(this.shopChannelDao);
        ret.setDivPayTransDao(this.divPayTransDao);
        ret.setPayAdaptor(this.factory);
        ret.setPayTransDao(this);
        return ret;
    }


    /**
     * 由id返回支付交易对象
     * @author Ming Qiu
     * <p>
     * date: 2022-11-06 23:31
     * @param id
     * @return
     * @throws RuntimeException
     * @throws BusinessException 无此对象
     */
    public PayTrans findById(Long shopId, Long id) throws RuntimeException{

        if (null == id) {
            throw new IllegalArgumentException("PayTransDao.findById: id is null");
        }
        PayTransPo po = payTransPoMapper.selectByPrimaryKey(id);
        if (null == po) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "支付交易", id));
        }
        if (!PLATFORM.equals(shopId) && !shopId.equals(po.getShopId())){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "支付交易", id, shopId));
        }
        return build(po);
    }

    /**
     * 按照shopChannelId查询
     * @param shopChannelIds shopChannel id list
     * @param transNo 渠道交易号
     * @param status 状态
     * @param beginTime 交易起时间
     * @param endTime 交易止时间
     * @param page 页
     * @param pageSize 每页数目
     * @return
     * @throws RuntimeException
     */
    public List<PayTrans> retrieveByShopChannelId(List<Long> shopChannelIds, String transNo, Integer status, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Integer pageSize) throws RuntimeException{

        PayTransPoExample example = new PayTransPoExample();
        PayTransPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopChannelIdIn(shopChannelIds);
        if (null != transNo) {
            criteria.andTransNoEqualTo(transNo);
        }
        if (null != status) {
            criteria.andStatusEqualTo(status.byteValue());
        }
        if (null != beginTime) {
            criteria.andTimeBeginGreaterThanOrEqualTo(beginTime);
        }
        if (null != endTime) {
            criteria.andTimeBeginLessThanOrEqualTo(endTime);
        }

        return retrievePayTrans(example, page, pageSize);
    }

    public List<PayTrans> retrieveByChannelId(Long channelId, String transNo, Integer status, LocalDateTime beginTime, LocalDateTime endTime, Integer page, Integer pageSize) throws RuntimeException{

        PayTransPoManualExample example = new PayTransPoManualExample();
        PayTransPoManualExample.Criteria criteria = example.createCriteria();
        criteria.andChannelIdEqualTo(channelId);
        if (null != transNo) {
            criteria.andTransNoEqualTo(transNo);
        }
        if (null != status) {
            criteria.andStatusEqualTo(status.byteValue());
        }
        if (null != beginTime) {
            criteria.andTimeBeginGreaterThanOrEqualTo(beginTime);
        }
        if (null != endTime) {
            criteria.andTimeBeginLessThanOrEqualTo(endTime);
        }

        PageHelper.startPage(page, pageSize, false);
        List<PayTransPo> poList = this.payTransPoManualMapper.selectByExample(example);
        return poList.stream().map(po -> this.build(po)).collect(Collectors.toList());

    }

    /**
     * 查询支付交易
     * @param example 查询条件
     * @param page 页
     * @param pageSize 每页数目
     * @return 支付交易
     */
    private List<PayTrans> retrievePayTrans(PayTransPoExample example, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, false);
        List<PayTransPo> poList = this.payTransPoMapper.selectByExample(example);
        return poList.stream().map(po -> this.build(po)).collect(Collectors.toList());
    }

    public Optional<PayTrans> findByOutNo(String outNo){
        if (null == outNo) {
            throw new IllegalArgumentException("outNo can not be null.");
        }
        PayTrans ret = null;
        PayTransPoExample example = new PayTransPoExample();
        PayTransPoExample.Criteria criteria = example.createCriteria();
        criteria.andOutNoEqualTo(outNo);
        List<PayTransPo> poList = this.payTransPoMapper.selectByExample(example);

        if(!poList.isEmpty()) {
            ret = build(poList.get(0));
        }
        return Optional.ofNullable(ret);
    }

    /**
     * 查询支付成功的交易
     * @param endTime successTime在endTime之前的
     * @param shopChannelId 支付渠道
     * @param page 页
     * @param pageSize 每页数目
     * @return
     */
    public List<PayTrans> retrieveSuccessPayTransBySuccessTimeBefore(Long shopChannelId, LocalDateTime endTime, Integer page, Integer pageSize){

        PayTransPoExample example = new PayTransPoExample();
        PayTransPoExample.Criteria criteria = example.createCriteria();
        criteria.andShopChannelIdEqualTo(shopChannelId);
        criteria.andStatusEqualTo(PayTrans.SUCCESS);
        criteria.andSuccessTimeLessThanOrEqualTo(endTime);
        PageHelper.startPage(page, pageSize, false);
        List<PayTransPo> poList = this.payTransPoMapper.selectByExample(example);
        return poList.stream().map(this::build).collect(Collectors.toList());
    }

    @Override
    protected Integer save(Transaction trans) {
        PayTrans obj;
        if (trans instanceof PayTrans) {
            obj = (PayTrans) trans;
        }else{
            throw new IllegalArgumentException("PayTransDao.save: trans should be Paytrans.");
        }
        PayTransPo po = CloneFactory.copy(new PayTransPo(), obj);
        logger.debug("save: po = {}", po);
        return payTransPoMapper.updateByPrimaryKeySelective(po);
    }

    @Override
    protected Transaction insert(Transaction trans) {
        logger.debug("insert: obj = {}", trans);
        PayTrans obj;
        if (trans instanceof PayTrans) {
            obj = (PayTrans) trans;
        }else{
            throw new IllegalArgumentException("PayTransDao.save: trans should be Paytrans.");
        }
        PayTransPo po = CloneFactory.copy(new PayTransPo(), obj);
        logger.debug("insert: po = {}", po);
        payTransPoMapper.insertSelective(po);
        obj.setId(po.getId());
        return obj;
    }
}
