package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.DefaultImageDO
import me.yxy.mydays.dao.pojo.SuggestionDO
import org.apache.ibatis.annotations.*

/**
 * 默认图片仓库
 */
@Mapper
public interface DefaultImagesMapper {

    /**
     * 一次性获取所有备选图片
     *
     */
    @Select("SELECT * FROM default_images ORDER BY id")
    fun getAllImages(): List<DefaultImageDO>

}