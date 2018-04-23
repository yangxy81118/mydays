package me.yxy.mydays.controller.vo.response

import java.util.*

/**
 * @param invitedCount 邀请人数，其实就是给该用户添加过生日的人数
 * @param daysCount 该用户在使用中的日期数
 */
data class UserView(var id:Int = 0,
                var openId:String = "",
                var createTime: Date? = null,
                var lastLoginTime: Date? = null,
                var daysCount:Int = 0,
                var nickName:String? = null,
                var avatarUrl:String? = null,
                    var limit:Int = 0,
                    var invitedCount:Int = 0)