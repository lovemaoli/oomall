//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.onsale;

import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获得当前有效的onsale
 */
public class ValidOnSaleExecutor implements OnSaleExecutor {
    private final static Logger logger = LoggerFactory.getLogger(ValidOnSaleExecutor.class);

    private OnSaleDao onsaleDao;

    private Long productId;

    public ValidOnSaleExecutor(OnSaleDao onsaleDao, Long productId) {
        this.onsaleDao = onsaleDao;
        this.productId = productId;
    }

    @Override
    public OnSale execute() {
        logger.debug("execute: productId = {}", this.productId);
        return this.onsaleDao.findLatestValidOnsaleByProductId(this.productId);
    }
}
