package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.CustomDay
import me.yxy.mydays.dao.pojo.Holiday
import org.apache.ibatis.annotations.*

/**
 * 节日DAO接口
 */
@Mapper
public interface CustomDayMapper {

    @Select("SELECT * FROM custom_days WHERE enable = 1 AND userId = #{userId}")
    fun findDayByUserId(@Param("userId") userId:Int):MutableList<CustomDay>

    @Select("SELECT * FROM custom_days WHERE enable = 1 AND id = #{id}")
    fun findDayId(@Param("userId") userId:Int):CustomDay?

    @Insert("INSERT INTO custom_days (userId,name,year,month,date,image) VALUES (#{userId},#{name},#{year},#{month},#{date},#{image})")
    fun addOne(day:CustomDay)

    @Update("UPDATE custom_days SET enable = 0 WHERE id = #{id}")
    fun removeOne(id:Int)

}