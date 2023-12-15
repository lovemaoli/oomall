//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.shop.controller.dto.ProductItemDto;
import cn.edu.xmu.oomall.shop.controller.dto.FreightPriceDto;
import cn.edu.xmu.oomall.shop.dao.TemplateDao;
import cn.edu.xmu.oomall.shop.dao.bo.ProductItem;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.Template;
import cn.edu.xmu.oomall.shop.dao.bo.template.TemplateResult;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@Service
public class FreightService {

    private final Logger logger = LoggerFactory.getLogger(FreightService.class);

    private RegionTemplateDao regionTemplateDao;

    private TemplateDao templateDao;

    @Autowired
    public FreightService(RegionTemplateDao regionTemplateDao, TemplateDao templateDao) {
        this.regionTemplateDao = regionTemplateDao;
        this.templateDao = templateDao;
    }

    /**
     * 计算一批商品的运费
     *
     * @param items
     * @param templateId 模板id
     * @param regionId   地区id
     */
    public FreightPriceDto cacuFreightPrice(List<ProductItem> items, Long templateId, Long regionId) {
        Template template = this.templateDao.findById(PLATFORM, templateId);
        RegionTemplate regionTemplate = this.regionTemplateDao.findByTemplateAndRegionId(template, regionId);
        logger.debug("getFreight: regionTemplate={}", regionTemplate);

        Collection<TemplateResult> ret = regionTemplate.calculate(items);
        long fee = ret.stream().mapToLong(pack -> pack.getFee()).sum();
        List<List<ProductItemDto>> packs = ret.stream().map(pack -> pack.getPack().stream().map(bo -> CloneFactory.copy(new ProductItemDto(), bo)).collect(Collectors.toList())).collect(Collectors.toList());
        packs = packs.stream().map(pack -> pack.stream().sorted(Comparator.comparingLong(ProductItemDto::getOrderItemId)).collect(Collectors.toList())).collect(Collectors.toList());
        return FreightPriceDto.builder().freightPrice(fee).pack(packs).build();
    }
}
