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

    val yearList = mutableListOf<ChineseYear>()

    private val PREFIX_YEAR = "YEAR:"

    private val PREFIX_MONTH = "MONTH:"

    /**
     * 用来存储“农历-实际日期的映射关系”,K-V举例：("1960（己亥年）,腊月,初五"->"1960-1-18")
     */
    private val chineseDateToNormalDate = mutableMapOf<String,String>()

    @PostConstruct
    fun init(){
        val resource = ClassPathResource("calendar")
        val reader = BufferedReader(InputStreamReader(resource.inputStream))

        var content = reader.readLine()

        var recentYear = ChineseYear()
        var monthWithDays = ""


        var recentYearTitle = ""
        var recentMonthTitle = ""

        while(content!=null){
            when {
                content.startsWith(PREFIX_YEAR) -> {
                    compressLastYear(recentYear)
                    recentYearTitle = content.replace(PREFIX_YEAR,"")
                    recentYear = ChineseYear(recentYearTitle,monthWithDays)
                    yearList.add(recentYear)
                }
                content.startsWith(PREFIX_MONTH) -> {
                    recentMonthTitle = content.replace(PREFIX_MONTH,"")
                    recentYear.m +="|$recentMonthTitle-"
                }
                else -> {
                    val info:List<String> = content.split(":")
                    val dayTitle = encodeDay(info[0])
                    recentYear.m += "$dayTitle,"

                    //存入map
                    chineseDateToNormalDate["$recentYearTitle,$recentMonthTitle,${info[0]}"] = info[1]
                }
            }
            content = reader.readLine()
        }

        compressLastYear(recentYear)

        println("finish!")
    }

    /**
     * 对每个月中初一到廿九的字母进行压缩
     */
    private fun compressLastYear(recentYear: ChineseYear) {
        if(recentYear.m.isEmpty()){
            return
        }

        recentYear.m = recentYear.m.replace("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,#,$,%","@")
    }


    private fun encodeDay(s: String): String {
        return when (s) {
            "初一" -> {
                "a"
            }
            "初二" -> {
                "b"
            }
            "初三" -> {
                "c"
            }
            "初四" -> {
                "d"
            }
            "初五" -> {
                "e"
            }
            "初六" -> {
                "f"
            }
            "初七" -> {
                "g"
            }
            "初八" -> {
                "h"
            }
            "初九" -> {
                "i"
            }
            "初十" -> {
                "j"
            }
            "十一" -> {
                "k"
            }
            "十二" -> {
                "l"
            }
            "十三" -> {
                "m"
            }
            "十四" -> {
                "n"
            }
            "十五" -> {
                "o"
            }
            "十六" -> {
                "p"
            }
            "十七" -> {
                "q"
            }
            "十八" -> {
                "r"
            }
            "十九" -> {
                "s"
            }
            "二十" -> {
                "t"
            }
            "廿一" -> {
                "u"
            }
            "廿二" -> {
                "v"
            }
            "廿三" -> {
                "w"
            }
            "廿四" -> {
                "x"
            }
            "廿五" -> {
                "y"
            }
            "廿六" -> {
                "z"
            }
            "廿七" -> {
                "#"
            }
            "廿八" -> {
                "$"
            }
            "廿九" -> {
                "%"
            }
            "三十" -> {
                "&"
            }
            else -> {
                s
            }
        }
    }

}

data class ChineseYear(var y:String = "",var m:String = "")