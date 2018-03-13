package me.yxy.mydays.controller.vo.request

/**
 * 添加自定义日期
 *
 * @param useLunar 是否使用农历
 * @param cycle 是否每年循环
 */
data class AddDay(val userId:Int = 0,val dayId:Int = 0,val title:String = "",val date:String = "",val useLunar:Boolean,val cycle:Boolean,val image:String = "")