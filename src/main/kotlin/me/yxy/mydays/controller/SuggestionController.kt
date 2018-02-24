package me.yxy.mydays.controller

import me.yxy.mydays.controller.vo.SomeDayView
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.mapper.HolidayMapper
import me.yxy.mydays.dao.mapper.SuggestionMapper
import me.yxy.mydays.dao.pojo.CustomDay
import me.yxy.mydays.dao.pojo.Holiday
import me.yxy.mydays.dao.pojo.Suggestion
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * 建议信息查询
 */
@RestController
@RequestMapping("/suggestion")
class SuggestionController {

    @Autowired
    lateinit var suggestionMapper: SuggestionMapper

    @Autowired
    lateinit var holidayMapper:HolidayMapper

    @Autowired
    lateinit var customMapper:CustomDayMapper

    val CUSTOM_DAY_ID_BASELINE:Int = 100000

    @GetMapping("/list")
    fun check(@RequestParam(value = "dayId") dayId:Int): List<Suggestion>? {

        var sugIds:List<String>? = null

        if(dayId < CUSTOM_DAY_ID_BASELINE){
            val holiday:Holiday? = holidayMapper.findHolidayById(dayId)
            sugIds = holiday?.suggestions?.split(",")
        }else{
            val customDay:CustomDay? = customMapper.findDayId(dayId)
            sugIds = customDay?.suggestions?.split(",")
        }

        if(sugIds==null){
            return null
        }

        var suggestionViews:List<Suggestion> = suggestionMapper.findByIdList(sugIds)
        return suggestionViews
    }

}