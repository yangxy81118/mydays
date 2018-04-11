package me.yxy.mydays.dao.pojo

import java.util.*

/**
 * 账户信息
 */
data class UserDO(var id:Int = 0,
                  var openId:String = "",
                  val createTime:Date? = null,
                  var lastLoginTime:Date? = null,
                  var state:Int = 1,
                  var limitCount:Int = 0,
                  var nickName:String? = null,
                  var avatarUrl:String? = null)