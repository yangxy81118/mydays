package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.vo.response.UserView
import me.yxy.mydays.service.CustomDayReqeust
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.UserService
import me.yxy.mydays.service.domain.User
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * 计算
 */
@Repository
class UserFetcher : GraphqlDataFetcherAdapter<UserView>() {

    @Autowired
    lateinit var userService:UserService

    @Autowired
    lateinit var customDayService:CustomDayService


    override fun get(environment: DataFetchingEnvironment): UserView {
        val userId:Int = environment.getArgument<Int>("userId")

        checkOwner(userId)

        val userDomain = userService.getUserInfo(userId)
        val userView = UserView()
        BeanUtils.copyProperties(userDomain,userView)

        if(fieldIsSelected(environment,"daysCount")){
            userView.daysCount = customDayService.countForUser(CustomDayReqeust(userId))
        }

        if(fieldIsSelected(environment,"invitedCount")){
            userView.invitedCount = customDayService.countInvited(userId)
        }

        return userView

    }
}