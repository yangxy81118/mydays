package me.yxy.mydays.dao.pojo

/**
 * 用户自定义日期
 */
data class CustomDayDO(val id:Int = 0,
                       val userId:Int = 0,
                       val name:String = "",
                       val year:Int = 0,
                       val month:Int = 0,
                       val date:Int = 0,
                       val image:String = "",
                       val engName:String = "",
                       val brief:String = "",
                       val lunar:String = "",
                       val suggestions:String? = null)