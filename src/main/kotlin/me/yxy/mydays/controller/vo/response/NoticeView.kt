package me.yxy.mydays.controller.vo.response

/**
 * 通知View
 */
data class NoticeView(val title:String,
                      val type:Int,
                      var content:List<String>?=null)