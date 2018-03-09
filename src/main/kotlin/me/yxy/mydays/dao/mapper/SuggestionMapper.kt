package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.SuggestionDO
import org.apache.ibatis.annotations.*

/**
 * 建议
 */
@Mapper
public interface SuggestionMapper {

    @Select("SELECT * FROM suggestion WHERE id=#{id}")
    fun findById(id:String): SuggestionDO?

    @Select("""
        <script>
            SELECT * FROM suggestion WHERE id IN
                <foreach item='item' index='index' collection='sugIds'
                 open='(' separator=',' close=')'>
                     #{item}
                </foreach>
        </script>
            """)
    fun findByIdList(@Param("sugIds") ids:List<Int>):List<SuggestionDO>

}