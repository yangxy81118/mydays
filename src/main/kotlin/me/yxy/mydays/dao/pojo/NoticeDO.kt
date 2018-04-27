package me.yxy.mydays.dao.pojo

import java.util.*


/**
 * 通知
 */
data class NoticeDO(val id:Int = 0,
                    val title:String = "",
                    val content:String = "",
                    val type:Int = 0,
                    val createTime:Date? = null)