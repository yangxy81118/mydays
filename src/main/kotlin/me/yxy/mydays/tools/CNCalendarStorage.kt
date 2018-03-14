package me.yxy.mydays.tools

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.annotation.PostConstruct

/**
 *
 */
@Component
class CNCalendarStorage{

    private val yearList = mutableListOf<ChineseYear>()

    private val PREFIX_YEAR = "YEAR:"

    private val PREFIX_MONTH = "MONTH:"

    @PostConstruct
    fun init(){
        val resource = ClassPathResource("calendar")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))

        var content = reader.readLine()

        var recentYear: ChineseYear
        var recentMonth: ChineseMonth
        var recentMonthOfYear = mutableListOf<ChineseMonth>()
        var recentDaysOfMonth = mutableListOf<ChineseDate>()

        while(content!=null){
            when {
                content.startsWith(PREFIX_YEAR) -> {
                    recentMonthOfYear = mutableListOf<ChineseMonth>()
                    val recentYearStr = content.replace(PREFIX_YEAR,"")
                    recentYear = ChineseYear(recentYearStr,recentMonthOfYear)
                    yearList.add(recentYear)
                }
                content.startsWith(PREFIX_MONTH) -> {
                    recentDaysOfMonth = mutableListOf<ChineseDate>()
                    var recentMonthStr = content.replace(PREFIX_MONTH,"")
                    recentMonth = ChineseMonth(recentMonthStr,recentDaysOfMonth)
                    recentMonthOfYear.add(recentMonth)
                }
                else -> {
                    val info:List<String> = content.split(":")
                    val recentDay = ChineseDate(info[0],info[1])
                    recentDaysOfMonth.add(recentDay)
                }
            }
            content = reader.readLine()
        }

        println("finish!")
    }

}

data class ChineseDate(var title:String = "",var normalDate:String = "")

data class ChineseMonth(var title:String = "",var days:List<ChineseDate> = mutableListOf())

data class ChineseYear(var title:String = "",var months:List<ChineseMonth> = mutableListOf())