package me.yxy.mydays.controller.vo.response

/**
 * 页面展示某一天数据
 *
 * @param age 年龄
 * @param greeting 问候语，这里会根据后台非常复杂的算法生成
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
                   var favor:Boolean = false,
                   var age:Int = 0,
                       var comment:String = "",
                       var greeting:String = "")