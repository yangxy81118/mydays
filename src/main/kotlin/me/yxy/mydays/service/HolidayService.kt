package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.HolidayMapper
import me.yxy.mydays.dao.pojo.HolidayDO
import me.yxy.mydays.service.domain.SomeDay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HolidayService{

    @Autowired
    private lateinit var holidayMapper: HolidayMapper

//    /**
//     * 获取指定Holiday
//     */
//    fun getHolidayById(dayId:Int):SomeDay?{
//
//        val holiday: HolidayDO? = holidayMapper.findHolidayById(dayId)
//
//        holiday?.let{
//            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
//            day.sugIds = formatSugIds(it.suggestions)
//            return day
//        }
//
//        return null
//    }
//
//    private fun formatSugIds(suggestions: String?): List<Int> {
//        val idsStr: List<String>? = suggestions?.split(",")
//
//        var ids  = mutableListOf<Int>()
//        idsStr?.forEach {
//            ids.add(it.toInt())
//        }
//
//        return ids
//
//    }
//
//    /**
//     * 获取所有有效holiday
//     */
//    fun getAllHolidays():List<SomeDay>{
//        val holidaySource:List<HolidayDO> = holidayMapper.findAllHolidays()
//
//        val dayList = mutableListOf<SomeDay>()
//        holidaySource.forEach{
//            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
//            day.sugIds = formatSugIds(it.suggestions)
//            dayList.add(day)
//        }
//
//        return dayList
//    }




}