package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.GreetingDO
import me.yxy.mydays.dao.pojo.SuggestionDO
import org.apache.ibatis.annotations.*

/**
 *  问候语
 */
@Mapper
interface GreetingMapper {

    @Select("""
        SELECT * FROM greeting
            """)
    fun findAll():List<GreetingDO>

}