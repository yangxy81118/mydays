package me.yxy.mydays.dao.pojo

/**
 * 特定的某一天
 */
data class HolidayDO(val id:Int = 0,
                   val name:String = "",
                   val year:Int = 0,
                   val month:Int = 0,
                   val date:Int = 0,
                   val image:String = "",
                   val engName:String = "",
                   val brief:String = "",
                   val lunar:String = "",
                   val suggestions:String? = null)

