package me.yxy.mydays.dao.pojo

import java.util.*

/**
 * 账户信息
 */
data class UserDO(val id:Int = 0,
                  var openId:String = "",
                  val createTime:Date? = null,
                  var lastLoginTime:Date? = null,
                  var limit:Int = 0)