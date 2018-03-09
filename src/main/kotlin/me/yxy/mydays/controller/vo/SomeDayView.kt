package me.yxy.mydays.controller.vo

import me.yxy.mydays.service.domain.Suggestion

/**
 * 页面展示某一天数据
 */
data class SomeDayView(var id:Int = 0,
                   var name:String = "",
                   var year:Int = 0,
                   var month:Int = 0,
                   var date:Int = 0,
                   var image:String = "",
                   var engName:String = "",
                       var brief:String = "",
                       var lunar:String = "",
                   var remain:Int = 0,
                   var custom:Boolean = false,
                   var suggestions:List<Suggestion> = listOf())