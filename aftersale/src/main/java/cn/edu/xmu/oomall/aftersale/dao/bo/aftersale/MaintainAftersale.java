package cn.edu.xmu.oomall.aftersale.dao.bo.aftersale;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;

public class MaintainAftersale extends Aftersale {

    @Override
    public ReturnNo shopAudit(Long shopid){
        //TODO 创建服务单
        return ReturnNo.OK;
    }
    public MaintainAftersale(Aftersale aftersale){
        this.setId(aftersale.getId());
        this.setType(aftersale.getType());
        this.setReason(aftersale.getReason());
        this.setConclusion(aftersale.getConclusion());
        this.setQuantity(aftersale.getQuantity());
        this.setContact(aftersale.getContact());
        this.setMobile(aftersale.getMobile());
        this.setAddress(aftersale.getAddress());
        this.setStatus(aftersale.getStatus());
        this.setGmt_apply(aftersale.getGmt_apply());
        this.setGmt_end(aftersale.getGmt_end());
        this.setOrder_id(aftersale.getOrder_id());
        this.setOrder_item_id(aftersale.getOrder_item_id());
        this.setProduct_item_id(aftersale.getProduct_item_id());
        this.setProduct_id(aftersale.getProduct_id());
        this.setShop_id(aftersale.getShop_id());
        this.setCustomer_id(aftersale.getCustomer_id());
        this.setIn_arbitration(aftersale.getIn_arbitration());
    }

}
