package me.yxy.mydays.controller

import me.yxy.mydays.tools.CNCalendarStorage
import me.yxy.mydays.tools.ChineseCalendar
import me.yxy.mydays.tools.ChineseYear
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder


/**
 * 由于一般查询都用在graphql，所以这里处理一些的简单查询
 */
@RestController
@RequestMapping("/simple-query")
class SimpleQueryController {

    @Autowired
    private lateinit var calanderStorage:CNCalendarStorage

    private val logger: Logger = LoggerFactory.getLogger(SimpleQueryController::class.java)

    @CrossOrigin
    @GetMapping("/lunar")
    fun getChineseCalendar():ResponseEntity<List<ChineseYear>>{
        return ResponseEntity.ok(calanderStorage.yearList)
    }


    @CrossOrigin
    @GetMapping("/lunar/normalTolunar")
    fun getLunarByNormalDate(@RequestParam("date") date:String):String{

        logger.info("normalTolunar:$date")

        //首先根据阳历，获取农历的描述
        val (normalYear,normalMonth,normalDay) = parseNormalDate(date)
        val cnDate = ChineseCalendar(normalYear,normalMonth-1,normalDay)

        //切分获取农历描述“X年”
        var (cnYear,cnMonth,cnDay) = parseCNDate(cnDate)

        //从storage中找出X年一共有哪些
        val similarYears:List<Int> = calanderStorage.findYearsByCNZODIAC(cnYear)

        //真正的年份一定是normalYear的前后一年或者本年
        similarYears.forEach {
            if(it <= normalYear+1 && it >= normalYear-1 ){
                cnYear = "${it}（$cnYear）"
                return@forEach
            }
        }

        return "$cnYear,$cnMonth,$cnDay"
    }

    private fun parseCNDate(cnDate: ChineseCalendar): CNDateVO {
        val chineseYear = cnDate.getChinese(ChineseCalendar.CHINESE_ZODIAC)+"年"
        val chineseMonth = better(cnDate.getChinese(ChineseCalendar.CHINESE_MONTH))
        val chineseDay = cnDate.getChinese(ChineseCalendar.CHINESE_DATE)
        return CNDateVO(chineseYear,chineseMonth,chineseDay)
    }

    private fun parseNormalDate(date: String): NormalDateVO {
        val array = date.split("-")
        return NormalDateVO(array[0].toInt(),array[1].toInt(),array[2].toInt())
    }

    @CrossOrigin
    @GetMapping("/lunar/lunarToNormal")
    fun getNormalFromLunar(@RequestParam("date") date:String):String{
        val decodedTime = URLDecoder.decode(date,"UTF-8")
        logger.info("lunarToNormal:$decodedTime")
        return calanderStorage.getNormalDateFromLunarDate(decodedTime) ?: ""
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
}


data class NormalDateVO(val y:Int =0, val m:Int = 0, val d:Int = 0)

data class CNDateVO(val y:String,val m:String,val d:String)