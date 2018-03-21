package me.yxy.mydays

import me.yxy.mydays.tools.ChineseCalendar
import org.joda.time.DateTime
import org.springframework.beans.BeanUtils
import java.io.File
import javax.xml.crypto.Data

fun main(args: Array<String>) {
    val loginName = "2015%EF%BC%88%E7%BE%8A%E5%B9%B4%EF%BC%89%E5%9B%9B%E6%9C%88%E5%88%9D%E4%B8%80"
    val a = java.net.URLDecoder.decode(loginName,"UTF-8")
    println(a)
}