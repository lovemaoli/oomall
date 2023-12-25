package cn.edu.xmu.oomall.aftersale.dao.bo.aftersale;

import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;

public class MaintainAftersale extends Aftersale {

    public MaintainAftersale(Aftersale aftersale){
        this.setId(aftersale.getId());
        this.setType(aftersale.getType());
        this.setStatus(aftersale.getStatus());
        this.setReason(aftersale.getReason());
        this.setQuantity(aftersale.getQuantity());
        this.setMobile(aftersale.getMobile());
        this.setMobile(aftersale.getMobile());
        this.setOrder_id(aftersale.getOrder_id());
        this.setOrder_item_id(aftersale.getOrder_item_id());
        this.setProduct_item_id(aftersale.getProduct_item_id());
        this.setProduct_id(aftersale.getProduct_id());
        this.setShop_id(aftersale.getShop_id());
        this.setCustomer_id(aftersale.getCustomer_id());
        this.setIn_arbitration(aftersale.getIn_arbitration());
    }

}
