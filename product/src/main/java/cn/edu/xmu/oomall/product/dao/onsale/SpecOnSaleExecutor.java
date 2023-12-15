//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.onsale;

import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * 获得某个特定的onsale
 */
public class SpecOnSaleExecutor implements OnSaleExecutor {
    private final static Logger logger = LoggerFactory.getLogger(SpecOnSaleExecutor.class);
    private OnSaleDao onsaleDao;

    private Long onsaleId;

    public SpecOnSaleExecutor(OnSaleDao onsaleDao, Long onsaleId) {
        this.onsaleDao = onsaleDao;
        this.onsaleId = onsaleId;
    }

    @Override
    public OnSale execute() {
        logger.debug("execute: onsaleId = {}", this.onsaleId);
        return this.onsaleDao.findById(PLATFORM, this.onsaleId);
    }
}
