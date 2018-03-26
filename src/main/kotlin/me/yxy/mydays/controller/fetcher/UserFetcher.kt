package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.service.CustomDayReqeust
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.UserService
import me.yxy.mydays.service.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * 计算
 */
@Repository
class UserFetcher : GraphqlDataFetcherAdapter<User>() {

    @Autowired
    lateinit var userService:UserService

    @Autowired
    lateinit var customDayService:CustomDayService


    override fun get(environment: DataFetchingEnvironment): User {
        val userId:Int = environment.getArgument<Int>("userId")

        val userDomain = userService.getUserInfo(userId)
        if(fieldIsSelected(environment,"daysCount")){
            userDomain.daysCount = customDayService.countForUser(CustomDayReqeust(userId))
        }

        return userDomain
    }
}