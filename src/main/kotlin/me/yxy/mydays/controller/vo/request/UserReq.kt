package me.yxy.mydays.controller.vo.request

/**
 * 用户
 *
 */
data class UserReq(var id:Int = 0,
                   val openId:String = "",
                   val nickName:String = "",
                   val avatarUrl:String = "",
                   val limit:Int = 30,
                   val state:Int = 1)
