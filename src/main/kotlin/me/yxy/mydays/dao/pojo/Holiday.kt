package me.yxy.mydays.dao.pojo

/**
 * 特定的某一天
 */
data class Holiday(val id:Int = 0,
                   val name:String = "",
                   val year:Int = 0,
                   val month:Int = 0,
                   val date:Int = 0,
                   val image:String = "",
                   val suggestions:String? = null)

