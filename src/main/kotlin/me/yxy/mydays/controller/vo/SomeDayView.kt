package me.yxy.mydays.controller.vo

/**
 * 页面展示某一天数据
 */
data class SomeDayView(val id:Int = 0,
val name:String = "",
var year:Int = 0,
val month:Int = 0,
val date:Int = 0,
val image:String = "",
val engName:String = "",val brief:String = "",val lunar:String = "",
                       var remain:Int = 0,
var custom:Boolean = false)