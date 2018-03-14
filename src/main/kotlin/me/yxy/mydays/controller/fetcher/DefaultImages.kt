package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 */
@Repository
class DefaultImages : GraphqlDataFetcherAdapter<List<String>>() {

    @Autowired
    lateinit var imageService: ImageService

    override fun get(environment: DataFetchingEnvironment): List<String>? {

        var images = imageService.getAllImages()

        val maxCount:Int = environment.getArgument<Int>("count") ?: 6

        var count = 0

        while(count < maxCount){
            val thisIdx:Int = (Math.random()*maxCount).toInt()

        }

        return images

    }


}