//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.model.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;


/**
 *  @author xincong yao

 */
/**
 * @author yuhao shi
 * @date 2023-12-09
 * dgn2-010-syh
 */
@NoArgsConstructor
@Data
public abstract class BaseCouponDiscount{

	private static Logger logger = LoggerFactory.getLogger(BaseCouponDiscount.class);

	protected Long value;

	protected BaseCouponLimitation couponLimitation;

	protected String className;

	public BaseCouponDiscount(Long value, BaseCouponLimitation limitation) {
		this.value = value;
		this.couponLimitation = limitation;
		this.className = this.getClass().getName();
	}


	public List<Item> compute(List<Item> items) {
		if (!this.couponLimitation.pass(items)) {
			for (Item oi : items) {
				oi.setCouponActivityId(null);
			}
			return items;
		}
		calcAndSetDiscount(items);
		return items;
	}

	public abstract void calcAndSetDiscount(List<Item> items);

	public static Optional<BaseCouponDiscount> getInstance(String jsonString){
		ObjectMapper mapper = new ObjectMapper();
		BaseCouponDiscount bc = null;
		JsonNode root = null;
		try {
			root = mapper.readTree(jsonString);

			String className = root.get("className").asText();
			bc = (BaseCouponDiscount) Class.forName(className).getConstructor().newInstance();

			String limitation = root.get("couponLimitation").toString();
			BaseCouponLimitation bl = BaseCouponLimitation.getInstance(limitation).orElse(null);
			Long value = Long.valueOf(root.get("couponLimitation").get("value").toString());
			bl.setValue(value);

			bc.setCouponLimitation(bl);
			bc.setValue(root.get("value").asLong());
			bc.setClassName(className);
		} catch (JsonProcessingException e) {
			logger.error("getInstance: JsonProcessingException strategy = {}", jsonString);
		} catch (ClassNotFoundException e) {
			logger.error("getInstance: ClassNotFoundException strategy = {}", jsonString);
		} catch (InvocationTargetException e) {
			logger.error("getInstance: InvocationTargetException strategy = {}", jsonString);
		} catch (InstantiationException e) {
			logger.error("getInstance: InstantiationException strategy = {}", jsonString);
		} catch (IllegalAccessException e) {
			logger.error("getInstance: IllegalAccessException strategy = {}", jsonString);
		} catch (NoSuchMethodException e) {
			logger.error("getInstance: NoSuchMethodException strategy = {}", jsonString);
		}
		return Optional.ofNullable(bc);
	}
}
