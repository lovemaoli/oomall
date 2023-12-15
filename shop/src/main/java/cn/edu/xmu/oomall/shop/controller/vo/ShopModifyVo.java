package cn.edu.xmu.oomall.shop.controller.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 */
@NoArgsConstructor
public class ShopModifyVo {
    /**
     * 联系人信息
     */
    ShopConsigneeVo consignee;

    /**
     * 商铺免邮金额
     */
    Integer freeThreshold;

    public ShopConsigneeVo getConsignee1() {
        return consignee;
    }

    public void setConsignee(ShopConsigneeVo consignee) {
        this.consignee = consignee;
    }

    public Integer getFreeThreshold() {
        return freeThreshold;
    }

    public void setFreeThreshold(Integer freeThreshold) {
        this.freeThreshold = freeThreshold;
    }
}
