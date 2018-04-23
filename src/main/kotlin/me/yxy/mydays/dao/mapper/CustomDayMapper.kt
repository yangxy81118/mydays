package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.CustomDayDO
import org.apache.ibatis.annotations.*

/**
 * 用户自定义日期DAO接口
 */
@Mapper
interface CustomDayMapper {

    @Select("""
            <script>
                SELECT * FROM custom_days
                WHERE enable = 1 AND userId = #{userId}
                <if test="favor">
                    AND favor = 1
                </if>
            </script>
            """)
    fun findDayByUserId(@Param("userId") userId:Int,@Param("favor") favor:Boolean = false):MutableList<CustomDayDO>?

    /**
     * 查询数量
     */
    @Select("""
            <script>
                SELECT ifnull(count(1),0) FROM custom_days
                WHERE enable = 1 AND userId = #{userId}
                <if test="favor">
                    AND favor = 1
                </if>
            </script>
            """)
    fun countForUser(day:CustomDayDO):Int

    @Select("SELECT * FROM custom_days WHERE enable = 1 AND id = #{dayId}")
    fun findDayId(@Param("dayId") dayId:Int):CustomDayDO?

    @Insert("""
        INSERT INTO custom_days (userId,name,year,month,date,image,lunar,favor,comment,creatorId)
         VALUES (#{userId},#{name},#{year},#{month},#{date},#{image},#{lunar},#{favor},#{comment},#{creatorId})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    fun addOne(day:CustomDayDO)


    /**
     * 更新某一天
     */
    @Update("""
        <script>
            UPDATE custom_days
            <set>
                <if test="name!=null">
                    name = #{name},
                </if>
                <if test="year!=null">
                    year = #{year},
                </if>
                <if test="month!=null">
                    month = #{month},
                </if>
                <if test="date!=null">
                    date = #{date},
                </if>
                <if test="lunar!=null">
                    lunar = #{lunar},
                </if>
                <if test="favor!=null">
                    favor = #{favor},
                </if>
                <if test="comment!=null">
                    comment = #{comment},
                </if>
                <if test="lastModifiedTime!=null">
                    lastModifiedTime = #{lastModifiedTime},
                </if>
            </set>
            WHERE id = #{id}
        </script>
        """)
    fun updateOne(day:CustomDayDO)


    /**
     * 删除某一天
     */
    @Update("UPDATE custom_days SET enable = 0 WHERE id = #{id}")
    fun removeOne(id:Int)

    /**
     * 查询“我”邀请过的人数
     */
    @Select("""
        SELECT ifnull(count(1),0) FROM
        (SELECT DISTINCT creatorId FROM custom_days WHERE userId = #{ownerId} AND creatorId != #{ownerId}) a
        """)
    fun countInvited(@Param("ownerId") ownerId:Int):Int

    /**
     * 查询contributor为owner一共贡献过多少个日子，包括被删除的
     */
    @Select("""
        SELECT ifnull(count(1),0) FROM `custom_days` where creatorId = #{contributorId} and userId = #{ownerId}
        """)
    fun countContributeForSomeone(@Param("contributorId") contributorId:Int, @Param("ownerId") ownerId:Int):Int
}