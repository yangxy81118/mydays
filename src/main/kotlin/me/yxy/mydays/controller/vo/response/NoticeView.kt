package me.yxy.mydays.controller.vo.response

import java.util.*

/**
 * 通知View
 */
data class NoticeView(val title:String,
                      val type:Int,
                      val id:Int,
                      var date:String = "",
                      var content:List<String>?=null)
