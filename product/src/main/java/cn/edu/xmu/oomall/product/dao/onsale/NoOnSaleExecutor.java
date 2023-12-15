//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.product.dao.onsale;

import cn.edu.xmu.oomall.product.dao.bo.OnSale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获得某个特定的onsale
 */
public class NoOnSaleExecutor implements OnSaleExecutor {
    private final static Logger logger = LoggerFactory.getLogger(NoOnSaleExecutor.class);
    public NoOnSaleExecutor() {
    }

    @Override
    public OnSale execute() {
        return OnSale.builder().id(OnSale.NOTEXIST).build();
    }
}
