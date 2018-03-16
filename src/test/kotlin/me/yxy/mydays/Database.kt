package me.yxy.mydays

import me.yxy.mydays.tools.ChineseCalendar
import org.joda.time.DateTime
import org.springframework.beans.BeanUtils
import java.io.File
import javax.xml.crypto.Data

fun main(args: Array<String>) {


    val yearMap = mutableMapOf<String, HashMap<String, List<String>>>()
    val monthMap = mutableMapOf<String, List<String>>()


    var startTime = DateTime(1910, 1, 1, 0, 0)

    var lastYear = ""
    var lastMonth = ""


    var file = File("E:\\calendar")
    if(file.exists()) file.delete()

    for (i in 1..42500) {


        var chinese = ChineseCalendar(startTime.year, startTime.monthOfYear - 1, startTime.dayOfMonth)

        val chineseYear = chinese.getChinese(ChineseCalendar.CHINESE_YEAR)
        val chineseMonth = better(chinese.getChinese(ChineseCalendar.CHINESE_MONTH))
        val chineseDay = chinese.getChinese(ChineseCalendar.CHINESE_DATE)

        if(lastYear != chineseYear){
            lastYear = chineseYear
            val str = "YEAR:${startTime.year}（$chineseYear）\r\n"
            println("Year:${startTime.year}-$chineseYear")
            file.appendText(str)
        }

        if(lastMonth != chineseMonth){
            lastMonth = chineseMonth
            val str = "MONTH:$chineseMonth\r\n"
            file.appendText(str)
        }

        file.appendText("$chineseDay:${startTime.year}-${startTime.monthOfYear}-${startTime.dayOfMonth}\r\n")
        startTime = startTime.plusDays(1)

    }


    println("finish!")
}

fun better(chinese: String?): String {
    return when(chinese){
        "十一月" -> {
            "冬月"
        }
        "十二月" -> {
            "腊月"
        }
        else -> {
            chinese!!
        }
    }
}
