package me.yxy.mydays

import org.springframework.beans.BeanUtils
import javax.xml.crypto.Data

data class Database(var a:Int = 1,val b:String = "b")

data class Service(var a:Int = 2,val b:String = "",val s:String = "ss")



fun main(args: Array<String>) {

    val dd:Database = Database(3)
    val sr:Service = Service()
    BeanUtils.copyProperties(dd,sr)
    println(dd.a)
    println(sr.a)
}