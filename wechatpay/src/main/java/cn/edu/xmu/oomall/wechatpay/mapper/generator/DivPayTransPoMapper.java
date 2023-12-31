package cn.edu.xmu.oomall.wechatpay.mapper.generator;

import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.DivPayTransPo;
import cn.edu.xmu.oomall.wechatpay.mapper.generator.po.DivPayTransPoExample;
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

public interface DivPayTransPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @Delete({
        "delete from wechatpay_div_pay_trans",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @Insert({
        "insert into wechatpay_div_pay_trans (`id`, `appid`, ",
        "`sub_mchid`, `transaction_id`, ",
        "`out_order_no`, `state`, ",
        "`success_time`)",
        "values (#{id,jdbcType=BIGINT}, #{appid,jdbcType=VARCHAR}, ",
        "#{subMchid,jdbcType=VARCHAR}, #{transactionId,jdbcType=VARCHAR}, ",
        "#{outOrderNo,jdbcType=VARCHAR}, #{state,jdbcType=VARCHAR}, ",
        "#{successTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=String.class)
    int insert(DivPayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @InsertProvider(type=DivPayTransPoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=String.class)
    int insertSelective(DivPayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @SelectProvider(type=DivPayTransPoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="appid", property="appid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="transaction_id", property="transactionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="out_order_no", property="outOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="state", property="state", jdbcType=JdbcType.VARCHAR),
        @Result(column="success_time", property="successTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<DivPayTransPo> selectByExample(DivPayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "`id`, `appid`, `sub_mchid`, `transaction_id`, `out_order_no`, `order_id`, `state`, ",
        "`success_time`",
        "from wechatpay_div_pay_trans",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="appid", property="appid", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="transaction_id", property="transactionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="out_order_no", property="outOrderNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.VARCHAR),
        @Result(column="state", property="state", jdbcType=JdbcType.VARCHAR),
        @Result(column="success_time", property="successTime", jdbcType=JdbcType.TIMESTAMP)
    })
    DivPayTransPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=DivPayTransPoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("row") DivPayTransPo row, @Param("example") DivPayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=DivPayTransPoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("row") DivPayTransPo row, @Param("example") DivPayTransPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @UpdateProvider(type=DivPayTransPoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DivPayTransPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatpay_div_pay_trans
     *
     * @mbg.generated
     */
    @Update({
        "update wechatpay_div_pay_trans",
        "set `appid` = #{appid,jdbcType=VARCHAR},",
          "`sub_mchid` = #{subMchid,jdbcType=VARCHAR},",
          "`transaction_id` = #{transactionId,jdbcType=VARCHAR},",
          "`out_order_no` = #{outOrderNo,jdbcType=VARCHAR},",
          "`order_id` = #{orderId,jdbcType=VARCHAR},",
          "`state` = #{state,jdbcType=VARCHAR},",
          "`success_time` = #{successTime,jdbcType=TIMESTAMP}",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DivPayTransPo row);
}