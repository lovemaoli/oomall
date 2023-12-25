package cn.edu.xmu.oomall.aftersale.dao.bo.aftersale;

import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.dao.bo.AftersaleExpress;
import cn.edu.xmu.oomall.aftersale.dao.bo.OrderItem;

public class ReturnAftersale extends Aftersale {

    @Override
    public ReturnNo examine(Long shopid, Boolean confirm){
        if(this.getShop_id().equals(shopid)){
            if(confirm){
                this.setStatus(Aftersale.PROCESSING);
                OrderItem orderItem = this.getOrderItemDao().findById(this.getOrder_id(), this.getOrder_item_id());
                this.getPaymentDao().createRefund(orderItem, shopid); //TODO
            }else{
                this.setStatus(Aftersale.FINISH);
                Long billcode = this.getExpressDao().createExpress(shopid); //TODO
                AftersaleExpress aftersaleExpress = new AftersaleExpress();
                aftersaleExpress.setAftersale_id(this.getId());
                aftersaleExpress.setBill_code(billcode);
                aftersaleExpress.setSender(1);
                aftersaleExpress.setStatus(0);
                this.getAftersaleExpressDao().insert(aftersaleExpress);
            }
            this.getAftersaleDao().save(this);
            return ReturnNo.OK;
        }else{
            return ReturnNo.AUTH_NO_RIGHT;
        }

    }

    public ReturnAftersale(Aftersale aftersale){
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
