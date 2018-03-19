package me.yxy.mydays.dao.pojo

/**
 * 用户自定义日期
 */
data class CustomDayDO(var id:Int = 0,
                       var userId:Int = 0,
                       var name:String = "",
                       var year:Int = 0,
                       var month:Int = 0,
                       var date:Int = 0,
                       var image:String = "",
                       var engName:String = "",
                       var brief:String = "",
                       var lunar:String = "",
                       var suggestions:String? = null,
                       var favor:Int = 0)