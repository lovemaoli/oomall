//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.shop.dao.bo.template.PieceTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@Document("regionTemplate")
@CopyFrom({PieceTemplate.class, RegionTemplate.class})
public class PieceTemplatePo {

    @MongoId
    private String objectId;

    private Integer firstItem;

    private Long firstItemPrice;

    private Integer additionalItems;

    private Long additionalItemsPrice;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Integer getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(Integer firstItem) {
        this.firstItem = firstItem;
    }

    public Long getFirstItemPrice() {
        return firstItemPrice;
    }

    public void setFirstItemPrice(Long firstItemPrice) {
        this.firstItemPrice = firstItemPrice;
    }

    public Integer getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(Integer additionalItems) {
        this.additionalItems = additionalItems;
    }

    public Long getAdditionalItemsPrice() {
        return additionalItemsPrice;
    }

    public void setAdditionalItemsPrice(Long additionalItemsPrice) {
        this.additionalItemsPrice = additionalItemsPrice;
    }
}
