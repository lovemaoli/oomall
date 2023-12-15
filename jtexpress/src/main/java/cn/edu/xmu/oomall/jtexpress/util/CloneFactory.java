package cn.edu.xmu.oomall.jtexpress.util;

import cn.edu.xmu.oomall.jtexpress.controller.vo.CancelOrderVo;
import cn.edu.xmu.oomall.jtexpress.controller.vo.OrderVo;
import cn.edu.xmu.oomall.jtexpress.controller.vo.OrderVoV2;
import cn.edu.xmu.oomall.jtexpress.controller.vo.PersonInfoVo;
import cn.edu.xmu.oomall.jtexpress.dao.bo.ApiAccount;
import cn.edu.xmu.oomall.jtexpress.dao.bo.Order;
import cn.edu.xmu.oomall.jtexpress.dao.bo.PersonInfo;
import cn.edu.xmu.oomall.jtexpress.dao.bo.TraceDetail;
import cn.edu.xmu.oomall.jtexpress.mapper.po.ApiAccountPo;
import cn.edu.xmu.oomall.jtexpress.mapper.po.OrderPo;
import cn.edu.xmu.oomall.jtexpress.mapper.po.PersonInfoPo;
import cn.edu.xmu.oomall.jtexpress.mapper.po.TraceDetailPo;

public final class CloneFactory {

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static OrderPo copy(OrderPo target, Order source) {
        target.setId(source.getId());
        target.setNetwork(source.getNetwork());
        target.setTxLogisticId(source.getTxLogisticId());
        target.setExpressType(source.getExpressType());
        target.setOrderType(source.getOrderType());
        target.setServiceType(source.getServiceType());
        target.setDeliveryType(source.getDeliveryType());
        target.setPayType(source.getPayType());
        target.setSendStartTime(source.getSendStartTime());
        target.setSendEndTime(source.getSendEndTime());
        target.setGoodsType(source.getGoodsType());
        target.setRealName(source.getRealName());
        target.setCustomsDeclaration(source.getCustomsDeclaration());
        target.setLength(source.getLength());
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setWeight(source.getWeight());
        target.setWeight(source.getWeight());
        target.setTotalQuantity(source.getTotalQuantity());
        target.setItemsValue(source.getItemsValue());
        target.setPriceCurrency(source.getPriceCurrency());
        target.setOfferFee(source.getOfferFee());
        target.setRemark(source.getRemark());
        target.setPostSiteCode(source.getPostSiteCode());
        target.setPostSiteName(source.getPostSiteName());
        target.setPostSiteAddress(source.getPostSiteAddress());
        target.setBillCode(source.getBillCode());
        target.setStatus(source.getStatus());
        target.setCustomerCode(source.getCustomerCode());
        target.setSenderId(source.getSender().getId());
        target.setReceiverId(source.getReceiver().getId());
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static PersonInfoPo copy(PersonInfoPo target, PersonInfo source) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setCompany(source.getCompany());
        target.setPostCode(source.getPostCode());
        target.setMailBox(source.getMailBox());
        target.setMobile(source.getMobile());
        target.setPhone(source.getPhone());
        target.setCountryCode(source.getCountryCode());
        target.setProv(source.getProv());
        target.setCity(source.getCity());
        target.setArea(source.getArea());
        target.setTown(source.getTown());
        target.setStreet(source.getStreet());
        target.setAddress(source.getAddress());
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static PersonInfo copy(PersonInfo target, PersonInfoVo source) {
        target.setName(source.getName());
        target.setCompany(source.getCompany());
        target.setPostCode(source.getPostCode());
        target.setMailBox(source.getMailBox());
        target.setMobile(source.getMobile());
        target.setPhone(source.getPhone());
        target.setCountryCode(source.getCountryCode());
        target.setProv(source.getProv());
        target.setCity(source.getCity());
        target.setArea(source.getArea());
        target.setTown(source.getTown());
        target.setStreet(source.getStreet());
        target.setAddress(source.getAddress());
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static PersonInfo copy(PersonInfo target, PersonInfoPo source) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setCompany(source.getCompany());
        target.setPostCode(source.getPostCode());
        target.setMailBox(source.getMailBox());
        target.setMobile(source.getMobile());
        target.setPhone(source.getPhone());
        target.setCountryCode(source.getCountryCode());
        target.setProv(source.getProv());
        target.setCity(source.getCity());
        target.setArea(source.getArea());
        target.setTown(source.getTown());
        target.setStreet(source.getStreet());
        target.setAddress(source.getAddress());
        target.setAlter(false);
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static Order copy(Order target, OrderVo source) {
        target.setCustomerCode(source.getCustomerCode());
        target.setDigest(source.getDigest());
        target.setNetwork(source.getNetwork());
        target.setTxLogisticId(source.getTxLogisticId());
        target.setExpressType(source.getExpressType());
        target.setOrderType(source.getOrderType());
        target.setServiceType(source.getServiceType());
        target.setDeliveryType(source.getDeliveryType());
        target.setPayType(source.getPayType());
        target.setSender(source.getSender());
        target.setReceiver(source.getReceiver());
        target.setSendStartTime(source.getSendStartTime());
        target.setSendEndTime(source.getSendEndTime());
        target.setGoodsType(source.getGoodsType());
        target.setRealName(source.getRealName());
        target.setCustomsDeclaration(source.getCustomsDeclaration());
        target.setLength(source.getLength());
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setWeight(source.getWeight());
        target.setTotalQuantity(source.getTotalQuantity());
        target.setItemsValue(source.getItemsValue());
        target.setPriceCurrency(source.getPriceCurrency());
        target.setOfferFee(source.getOfferFee());
        target.setRemark(source.getRemark());
        target.setPostSiteCode(source.getPostSiteCode());
        target.setPostSiteName(source.getPostSiteName());
        target.setPostSiteAddress(source.getPostSiteAddress());
        return target;
    }

    public static Order copy(Order target, OrderVoV2 source) {
        target.setCustomerCode(source.getCustomerCode());
        target.setDigest(source.getDigest());
        target.setNetwork(source.getNetwork());
        target.setTxLogisticId(source.getTxLogisticId());
        target.setExpressType(source.getExpressType());
        target.setOrderType(source.getOrderType());
        target.setServiceType(source.getServiceType());
        target.setDeliveryType(source.getDeliveryType());
        target.setPayType(source.getPayType());
        target.setSender(source.getSender());
        target.setReceiver(source.getReceiver());
        target.setSendStartTime(source.getSendStartTime());
        target.setSendEndTime(source.getSendEndTime());
        target.setGoodsType(source.getGoodsType());
        target.setRealName(source.getRealName());
        target.setCustomsDeclaration(source.getCustomsDeclaration());
        target.setLength(source.getLength());
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setWeight(source.getWeight());
        target.setTotalQuantity(source.getTotalQuantity());
        target.setItemsValue(source.getItemsValue());
        target.setPriceCurrency(source.getPriceCurrency());
        target.setOfferFee(source.getOfferFee());
        target.setRemark(source.getRemark());
        target.setPostSiteCode(source.getPostSiteCode());
        target.setPostSiteName(source.getPostSiteName());
        target.setPostSiteAddress(source.getPostSiteAddress());
        target.setBillCode(source.getBillCode());
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static Order copy(Order target, OrderPo source) {
        target.setId(source.getId());
        target.setCustomerCode(source.getCustomerCode());
        target.setNetwork(source.getNetwork());
        target.setTxLogisticId(source.getTxLogisticId());
        target.setExpressType(source.getExpressType());
        target.setOrderType(source.getOrderType());
        target.setServiceType(source.getServiceType());
        target.setDeliveryType(source.getDeliveryType());
        target.setPayType(source.getPayType());
        target.setSendStartTime(source.getSendStartTime());
        target.setSendEndTime(source.getSendEndTime());
        target.setGoodsType(source.getGoodsType());
        target.setRealName(source.getRealName());
        target.setCustomsDeclaration(source.getCustomsDeclaration());
        target.setLength(source.getLength());
        target.setWidth(source.getWidth());
        target.setHeight(source.getHeight());
        target.setWeight(source.getWeight());
        target.setSender(source.getSenderId());
        target.setReceiver(source.getReceiverId());
        target.setTotalQuantity(source.getTotalQuantity());
        target.setItemsValue(source.getItemsValue());
        target.setPriceCurrency(source.getPriceCurrency());
        target.setOfferFee(source.getOfferFee());
        target.setRemark(source.getRemark());
        target.setPostSiteCode(source.getPostSiteCode());
        target.setPostSiteName(source.getPostSiteName());
        target.setPostSiteAddress(source.getPostSiteAddress());
        target.setBillCode(source.getBillCode());
        target.setStatus(source.getStatus());
        return target;
    }

    /**
     * Copy all fields from source to target
     *
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static ApiAccount copy(ApiAccount target, ApiAccountPo source) {
        target.setId(source.getId());
        target.setAccount(source.getAccount());
        target.setPrivateKey(source.getPrivateKey());
        return target;
    }

    /**
     * Copy all fields from source to target
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static Order copy(Order target, CancelOrderVo source) {
        target.setCustomerCode(source.getCustomerCode());
        target.setDigest(source.getDigest());
        target.setTxLogisticId(source.getTxLogisticId());
        target.setOrderType(source.getOrderType());
        return target;
    }

    /**
     * Copy all fields from source to target
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static TraceDetail copy(TraceDetail target, TraceDetailPo source) {
        target.setId(source.getId());
        target.setBillCode(source.getBillCode());
        target.setScanTime(source.getScanTime());
        target.setDesc(source.getDesc());
        target.setScanType(source.getScanType());
        target.setProblemType(source.getProblemType());
        target.setStaffName(source.getStaffName());
        target.setStaffContact(source.getStaffContact());
        target.setScanNetworkId(source.getScanNetworkId());
        target.setNextNetworkId(source.getNextNetworkId());
        target.setRebackStatus(source.getRebackStatus());
        target.setNetworkType(source.getNetworkType());
        target.setSignByOthersType(source.getSignByOthersType());
        target.setSignByOthersName(source.getSignByOthersName());
        target.setSignByOthersTel(source.getSignByOthersTel());
        target.setPickCode(source.getPickCode());
        return target;
    }

    /**
     * Copy all fields from source to target
     * @param target the target object
     * @param source the source object
     * @return the copied target object
     */
    public static TraceDetailPo copy(TraceDetailPo target, TraceDetail source) {
        target.setId(source.getId());
        target.setBillCode(source.getBillCode());
        target.setScanTime(source.getScanTime());
        target.setDesc(source.getDesc());
        target.setScanType(source.getScanType());
        target.setProblemType(source.getProblemType());
        target.setStaffName(source.getStaffName());
        target.setStaffContact(source.getStaffContact());
        target.setScanNetworkId(source.getScanNetworkId());
        target.setNextNetworkId(source.getNextNetworkId());
        target.setRebackStatus(source.getRebackStatus());
        target.setNetworkType(source.getNetworkType());
        target.setSignByOthersType(source.getSignByOthersType());
        target.setSignByOthersName(source.getSignByOthersName());
        target.setSignByOthersTel(source.getSignByOthersTel());
        target.setPickCode(source.getPickCode());
        return target;
    }
}


