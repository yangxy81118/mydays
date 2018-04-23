package me.yxy.mydays.service.domain

import java.util.*

data class User(var id:Int = 0,
                var openId:String = "",
                var createTime:Date? = null,
                var lastLoginTime:Date? = null,
                var limit:Int = 0,
                var nickName:String? = null,
                var avatarUrl:String? = null)
