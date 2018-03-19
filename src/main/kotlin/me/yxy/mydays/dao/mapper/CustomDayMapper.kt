package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.CustomDayDO
import org.apache.ibatis.annotations.*

/**
 * 用户自定义日期DAO接口
 */
@Mapper
public interface CustomDayMapper {

    @Select("SELECT * FROM custom_days WHERE enable = 1 AND userId = #{userId}")
    fun findDayByUserId(@Param("userId") userId:Int):MutableList<CustomDayDO>?

    @Select("SELECT * FROM custom_days WHERE enable = 1 AND id = #{dayId}")
    fun findDayId(@Param("dayId") dayId:Int):CustomDayDO?

    @Insert("""
        INSERT INTO custom_days (userId,name,year,month,date,image,lunar,favor)
         VALUES (#{userId},#{name},#{year},#{month},#{date},#{image},#{lunar},#{favor})
        """)
    fun addOne(day:CustomDayDO)


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
                <if test="image!=null">
                    image = #{image},
                </if>
            </set>
            WHERE id = #{dayId}
        </script>
        """)
    fun updateOne(day:CustomDayDO)


    @Update("UPDATE custom_days SET enable = 0 WHERE id = #{id}")
    fun removeOne(id:Int)

}