package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * 给他们贡献记录统计
 */
@Repository
class ContributeFetcher : GraphqlDataFetcherAdapter<Int>() {

    @Autowired
    lateinit var customerDayService : CustomDayService

    override fun get(environment: DataFetchingEnvironment): Int? {

        val contributeId:Int = environment.getArgument<Int>("contributorId")
        val ownerId:Int = environment.getArgument<Int>("ownerId")

        val count = customerDayService.countContributeForSomeone(contributeId,ownerId)

        return count

    }


}