package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.Holiday
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select


/**
 * 节日DAO接口
 */
@Mapper
public interface HolidayMapper{

    @Select("SELECT * FROM holiday WHERE enable = 1")
    fun findAllHolidays():MutableList<Holiday>

    @Select("SELECT * FROM holiday WHERE enable = 1 AND id = #{id}")
    fun findHolidayById(id:Int):Holiday?

}