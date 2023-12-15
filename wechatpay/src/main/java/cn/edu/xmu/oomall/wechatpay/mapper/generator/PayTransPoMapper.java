package cn.edu.xmu.oomall.wechatpay.mapper.generator;

import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPo;
import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.PayTransPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface PayTransPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @Delete({
        "delete from wechatpay_pay_trans",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @Insert({
        "insert into wechatpay_pay_trans (`id`, `sp_appid`, ",
        "`sp_mchid`, `sub_mchid`, ",
        "`description`, `out_trade_no`, ",
        "`time_expire`, `payer_sp_openid`, ",
        "`amount_total`, `trade_state`, ",
        "`trade_state_desc`, `success_time`)",
        "values (#{id,jdbcType=BIGINT}, #{spAppid,jdbcType=VARCHAR}, ",
        "#{spMchid,jdbcType=VARCHAR}, #{subMchid,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{outTradeNo,jdbcType=VARCHAR}, ",
        "#{timeExpire,jdbcType=TIMESTAMP}, #{payerSpOpenid,jdbcType=VARCHAR}, ",
        "#{amountTotal,jdbcType=INTEGER}, #{tradeState,jdbcType=VARCHAR}, ",
        "#{tradeStateDesc,jdbcType=VARCHAR}, #{successTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="transactionId", before=false, resultType=String.class)
    int insert(PayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @InsertProvider(type=PayTransPoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="transactionId", before=false, resultType=String.class)
    int insertSelective(PayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @SelectProvider(type=PayTransPoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="sp_appid", property="spAppid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sp_mchid", property="spMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="out_trade_no", property="outTradeNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_expire", property="timeExpire", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="payer_sp_openid", property="payerSpOpenid", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount_total", property="amountTotal", jdbcType=JdbcType.INTEGER),
        @Result(column="transaction_id", property="transactionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="trade_state", property="tradeState", jdbcType=JdbcType.VARCHAR),
        @Result(column="trade_state_desc", property="tradeStateDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="success_time", property="successTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<PayTransPo> selectByExample(PayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "`id`, `sp_appid`, `sp_mchid`, `sub_mchid`, `description`, `out_trade_no`, `time_expire`, ",
        "`payer_sp_openid`, `amount_total`, `transaction_id`, `trade_state`, `trade_state_desc`, ",
        "`success_time`",
        "from wechatpay_pay_trans",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="sp_appid", property="spAppid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sp_mchid", property="spMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="out_trade_no", property="outTradeNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="time_expire", property="timeExpire", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="payer_sp_openid", property="payerSpOpenid", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount_total", property="amountTotal", jdbcType=JdbcType.INTEGER),
        @Result(column="transaction_id", property="transactionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="trade_state", property="tradeState", jdbcType=JdbcType.VARCHAR),
        @Result(column="trade_state_desc", property="tradeStateDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="success_time", property="successTime", jdbcType=JdbcType.TIMESTAMP)
    })
    PayTransPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PayTransPoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("row") PayTransPo row, @Param("example") PayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PayTransPoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("row") PayTransPo row, @Param("example") PayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PayTransPoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(PayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_pay_trans
     *
     * @mbg.generated
     */
    @Update({
        "update wechatpay_pay_trans",
        "set `sp_appid` = #{spAppid,jdbcType=VARCHAR},",
          "`sp_mchid` = #{spMchid,jdbcType=VARCHAR},",
          "`sub_mchid` = #{subMchid,jdbcType=VARCHAR},",
          "`description` = #{description,jdbcType=VARCHAR},",
          "`out_trade_no` = #{outTradeNo,jdbcType=VARCHAR},",
          "`time_expire` = #{timeExpire,jdbcType=TIMESTAMP},",
          "`payer_sp_openid` = #{payerSpOpenid,jdbcType=VARCHAR},",
          "`amount_total` = #{amountTotal,jdbcType=INTEGER},",
          "`transaction_id` = #{transactionId,jdbcType=VARCHAR},",
          "`trade_state` = #{tradeState,jdbcType=VARCHAR},",
          "`trade_state_desc` = #{tradeStateDesc,jdbcType=VARCHAR},",
          "`success_time` = #{successTime,jdbcType=TIMESTAMP}",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PayTransPo row);
}