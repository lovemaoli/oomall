package cn.edu.xmu.oomall.payment.mapper.generator;

import cn.edu.xmu.oomall.payment.mapper.generator.po.ShopChannelPo;
import cn.edu.xmu.oomall.payment.mapper.generator.po.ShopChannelPoExample;
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

public interface ShopChannelPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @Delete({
        "delete from payment_shop_channel",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @Insert({
        "insert into payment_shop_channel (`shop_id`, `sub_mchid`, ",
        "`channel_id`, `creator_id`, ",
        "`creator_name`, `modifier_id`, ",
        "`modifier_name`, `gmt_create`, ",
        "`gmt_modified`, `status`)",
        "values (#{shopId,jdbcType=BIGINT}, #{subMchid,jdbcType=VARCHAR}, ",
        "#{channelId,jdbcType=BIGINT}, #{creatorId,jdbcType=BIGINT}, ",
        "#{creatorName,jdbcType=VARCHAR}, #{modifierId,jdbcType=BIGINT}, ",
        "#{modifierName,jdbcType=VARCHAR}, #{gmtCreate,jdbcType=TIMESTAMP}, ",
        "#{gmtModified,jdbcType=TIMESTAMP}, #{status,jdbcType=TINYINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(ShopChannelPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @InsertProvider(type=ShopChannelPoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(ShopChannelPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @SelectProvider(type=ShopChannelPoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="shop_id", property="shopId", jdbcType=JdbcType.BIGINT),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel_id", property="channelId", jdbcType=JdbcType.BIGINT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.BIGINT),
        @Result(column="creator_name", property="creatorName", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifier_id", property="modifierId", jdbcType=JdbcType.BIGINT),
        @Result(column="modifier_name", property="modifierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    List<ShopChannelPo> selectByExample(ShopChannelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "`id`, `shop_id`, `sub_mchid`, `channel_id`, `creator_id`, `creator_name`, `modifier_id`, ",
        "`modifier_name`, `gmt_create`, `gmt_modified`, `status`",
        "from payment_shop_channel",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="shop_id", property="shopId", jdbcType=JdbcType.BIGINT),
        @Result(column="sub_mchid", property="subMchid", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel_id", property="channelId", jdbcType=JdbcType.BIGINT),
        @Result(column="creator_id", property="creatorId", jdbcType=JdbcType.BIGINT),
        @Result(column="creator_name", property="creatorName", jdbcType=JdbcType.VARCHAR),
        @Result(column="modifier_id", property="modifierId", jdbcType=JdbcType.BIGINT),
        @Result(column="modifier_name", property="modifierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    ShopChannelPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @UpdateProvider(type=ShopChannelPoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("row") ShopChannelPo row, @Param("example") ShopChannelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @UpdateProvider(type=ShopChannelPoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("row") ShopChannelPo row, @Param("example") ShopChannelPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @UpdateProvider(type=ShopChannelPoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ShopChannelPo row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_shop_channel
     *
     * @mbg.generated
     */
    @Update({
        "update payment_shop_channel",
        "set `shop_id` = #{shopId,jdbcType=BIGINT},",
          "`sub_mchid` = #{subMchid,jdbcType=VARCHAR},",
          "`channel_id` = #{channelId,jdbcType=BIGINT},",
          "`creator_id` = #{creatorId,jdbcType=BIGINT},",
          "`creator_name` = #{creatorName,jdbcType=VARCHAR},",
          "`modifier_id` = #{modifierId,jdbcType=BIGINT},",
          "`modifier_name` = #{modifierName,jdbcType=VARCHAR},",
          "`gmt_create` = #{gmtCreate,jdbcType=TIMESTAMP},",
          "`gmt_modified` = #{gmtModified,jdbcType=TIMESTAMP},",
          "`status` = #{status,jdbcType=TINYINT}",
        "where `id` = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ShopChannelPo row);
}