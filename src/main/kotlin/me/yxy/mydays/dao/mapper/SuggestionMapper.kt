package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.Suggestion
import me.yxy.mydays.dao.pojo.User
import org.apache.ibatis.annotations.*

/**
 * 建议
 */
@Mapper
public interface SuggestionMapper {

    @Select("SELECT * FROM suggestion WHERE id=#{id}")
    fun findById(id:String):Suggestion?

    @Select("""
        <script>
            SELECT * FROM suggestion WHERE id IN
                <foreach item='item' index='index' collection='sugIds'
                 open='(' separator=',' close=')'>
                     #{item}
                </foreach>
        </script>
            """)
    fun findByIdList(@Param("sugIds") ids:List<String>):List<Suggestion>

}