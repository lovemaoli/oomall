//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.shop.dao.bo.divide;

import cn.edu.xmu.oomall.shop.ShopTestApplication;
import cn.edu.xmu.oomall.shop.dao.bo.ProductItem;
import cn.edu.xmu.oomall.shop.dao.bo.template.PieceTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.RegionTemplate;
import cn.edu.xmu.oomall.shop.dao.bo.template.WeightTemplate;
import cn.edu.xmu.oomall.shop.dao.template.RegionTemplateDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 平均分包策略测试
 * ZhaoDong Wang
 * 2023-dgn1-009
 */
@SpringBootTest(classes = ShopTestApplication.class)
public class AverageDivideStrategyTest {

    @Autowired
    RegionTemplateDao regionTemplateDao;

    /**
     * 平均简单分包(计重）
     */
    @Test
    public void testAverageDivideStrategyWhenSimpleWeigh() {
        PackAlgorithm algorithm = new SimpleAlgorithm();
        DivideStrategy divideStrategy = new AverageDivideStrategy(algorithm);
        List<ProductItem> items = new ArrayList<>() {
            {
                add(new ProductItem(1L, 1L, 100L, 1, 1L, 1));
                add(new ProductItem(2L, 2L, 100L, 2, 1L, 2));
                add(new ProductItem(3L, 3L, 100L, 3, 1L, 3));
                add(new ProductItem(4L, 4L, 100L, 4, 1L, 4));
                add(new ProductItem(5L, 5L, 100L, 5, 1L, 3));
                add(new ProductItem(6L, 6L, 100L, 6, 1L, 2));
                add(new ProductItem(7L, 7L, 100L, 7, 1L, 1));
            }
        };
        RegionTemplate regionTemplate = new WeightTemplate() {
            {
                setUpperLimit(12);
                setTemplateId(1L);
            }
        };
        regionTemplateDao.build(regionTemplate);

        Collection<Collection<ProductItem>> packs = divideStrategy.divide(regionTemplate, items);

        assertNotNull(packs);
        // size=64/(64/12+1)=10
        packs.stream().forEach(pack -> {
            assertTrue(pack.stream().map(item -> item.getWeight() * item.getQuantity()).reduce((x, y) -> x + y).get() <= 10);
        });
        assertEquals(64, packs.stream().map(pack -> pack.stream().map(item -> item.getWeight() * item.getQuantity()).reduce((x, y) -> x + y).get()).reduce((x, y) -> x + y).get());
        assertEquals(12, packs.stream().map(pack -> pack.size()).reduce((x, y) -> x + y).get());
        assertEquals(8, packs.size());
    }

    /**
     * 平均简单分包(计件）
     */
    @Test
    public void testAverageDivideStrategyWhenSimplePiece() {
        PackAlgorithm algorithm = new SimpleAlgorithm();
        DivideStrategy divideStrategy = new AverageDivideStrategy(algorithm);
        List<ProductItem> items = new ArrayList<>() {
            {
                add(new ProductItem(1L, 1L, 100L, 1, 1L, 1));
                add(new ProductItem(2L, 2L, 100L, 2, 1L, 2));
                add(new ProductItem(3L, 3L, 100L, 3, 1L, 3));
                add(new ProductItem(4L, 4L, 100L, 4, 1L, 4));
                add(new ProductItem(5L, 5L, 100L, 5, 1L, 3));
                add(new ProductItem(6L, 6L, 100L, 6, 1L, 2));
                add(new ProductItem(7L, 7L, 100L, 7, 1L, 1));
            }
        };
        RegionTemplate regionTemplate = new PieceTemplate() {
            {
                setUpperLimit(6);
                setTemplateId(2L);
            }
        };
        regionTemplateDao.build(regionTemplate);

        Collection<Collection<ProductItem>> packs = divideStrategy.divide(regionTemplate, items);

        assertNotNull(packs);
        // size=16/(16/6+1)=5
        packs.stream().forEach(pack -> {
            assertTrue(pack.stream().map(item -> item.getQuantity()).reduce((x, y) -> x + y).get() <= 5);
        });
        assertEquals(16, packs.stream().map(pack -> pack.stream().map(item -> item.getQuantity()).reduce((x, y) -> x + y).get()).reduce((x, y) -> x + y).get());
        assertEquals(8, packs.stream().map(pack -> pack.size()).reduce((x, y) -> x + y).get());
        assertEquals(4, packs.size());
    }

}
