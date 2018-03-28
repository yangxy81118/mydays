package me.yxy.mydays.controller.vo.request

/**
 * 添加自定义日期
 *
 * @param useLunar 是否使用农历
 * @param cycle 是否每年循环
 */
data class AddDay(val userId:Int = 0,
                  val dayId:Int = 0,
                  val name:String = "",
                  val date:String = "",
                  val dateMode:Int = 0,
                  val favor:Boolean = false,
                  val comment:String = ""
                  )