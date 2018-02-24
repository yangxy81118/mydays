package me.yxy.mydays.controller

import me.yxy.mydays.controller.vo.SomeDayView
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.mapper.HolidayMapper
import me.yxy.mydays.dao.pojo.CustomDay
import me.yxy.mydays.dao.pojo.Holiday
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


/**
 * 日历信息查询
 */
@RestController
@RequestMapping("/days")
class DaysController {

    @Autowired
    lateinit var holidayMapper:HolidayMapper

    @Autowired
    lateinit var customDayMapper:CustomDayMapper


    @GetMapping("/get")
    fun getOneDay(@RequestParam(value = "dayId") dayId:Int): SomeDayView? {
        val source: Holiday = holidayMapper.findHolidayById(dayId) ?: return null

        //Holidays
        var viewItem = SomeDayView(source?.id,source?.name,source?.year,source?.month,source?.date,source?.image)
        findRemainDays(viewItem)

        return viewItem
    }

    @GetMapping("/list")
    fun listDays(@RequestParam(value = "userId") userId: String): MutableList<SomeDayView> {
        val holidaySource:MutableList<Holiday> = holidayMapper.findAllHolidays()

        //Holidays
        val dayViews = mutableListOf<SomeDayView>()
        holidaySource.forEach{
            var viewItem = SomeDayView(it.id,it.name,it.year,it.month,it.date,it.image)
            findRemainDays(viewItem)
            dayViews.add(viewItem)
        }

        //Custom Days
        val customDays:MutableList<CustomDay> = customDayMapper.findDayByUserId(Integer.parseInt(userId))
        customDays.forEach{
            var viewItem = SomeDayView(it.id,it.name,it.year,it.month,it.date,it.image)
            val isInFuture:Boolean = findRemainDays(viewItem)
            if(isInFuture){
                viewItem.custom = true
                dayViews.add(viewItem)
            }
        }

        //Sort
        Collections.sort(dayViews,{firstOne,secondOne -> firstOne.remain - secondOne.remain})

        return dayViews
    }

    //TODO 当天的判断是有bug的
    private fun findRemainDays(viewItem: SomeDayView) : Boolean {
        val now:DateTime = DateTime.now()
        var year:Int = now.year

        val dayTemp:DateTime = DateTime(year,viewItem.month,viewItem.date,0,0,0)
        //如果晚于当前时间，则不需要+1

        if(viewItem.year!=0){
            year = viewItem.year
        }else{
            if(dayTemp.isBeforeNow) year++
            viewItem.year = year
        }

        val dayTime = DateTime(year,viewItem.month,viewItem.date,0,0,0)
        viewItem.remain = Days.daysBetween(now,dayTime).days

        if(viewItem.remain < 0){
            return false
        }

        if(viewItem.remain > 365) viewItem.remain -= 365
        return true
    }

}

