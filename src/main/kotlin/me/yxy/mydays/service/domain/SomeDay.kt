package me.yxy.mydays.service.domain

data class SomeDay(val id:Int = 0,
                     val userId:Int = 0,
                     val name:String = "",
                     var year:Int = 0,
                     val month:Int = 0,
                     val date:Int = 0,
                     val image:String = "",
                     val engName:String = "",
                     val brief:String = "",
                     val lunar:String = "",
                     var sugIds:List<Int>? = null,
                     var favor:Boolean = false,
                     var comment:String = "")