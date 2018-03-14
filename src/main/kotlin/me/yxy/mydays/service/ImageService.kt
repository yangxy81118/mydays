package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.DefaultImagesMapper
import me.yxy.mydays.dao.mapper.SuggestionMapper
import me.yxy.mydays.dao.pojo.SuggestionDO
import me.yxy.mydays.service.domain.Suggestion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 图片服务
 */
@Service
class ImageService{

    @Autowired
    private lateinit var defaultImagesMapper: DefaultImagesMapper


    /**
     * 获取所有备选图片
     */
    fun getAllImages():List<String>{
        val images = defaultImagesMapper.getAllImages()

        val imageUrls = mutableListOf<String>()
        images.forEach {
            imageUrls.add(it.url)
        }




        return imageUrls

    }
}