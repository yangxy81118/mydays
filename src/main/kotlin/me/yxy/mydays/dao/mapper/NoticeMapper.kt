package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.GreetingDO
import me.yxy.mydays.dao.pojo.NoticeDO
import me.yxy.mydays.dao.pojo.SuggestionDO
import org.apache.ibatis.annotations.*

/**
 *  公告
 */
@Mapper
interface NoticeMapper {

    @Select("""
            <script>
                SELECT * FROM notice
                <where>
                    <if test="query.type > 0">
                        type = #{query.type}
                    </if>
                </where>
                order by createTime desc
                limit #{startRow},#{rows}
            </script>
            """)
    fun page(@Param("query") query:NoticeDO , @Param("startRow") startRow:Int,@Param("rows") rows:Int):MutableList<NoticeDO>

}