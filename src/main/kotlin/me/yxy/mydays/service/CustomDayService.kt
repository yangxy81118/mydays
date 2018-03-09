package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.mapper.HolidayMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.dao.pojo.HolidayDO
import me.yxy.mydays.service.domain.SomeDay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomDayService {

    @Autowired
    private lateinit var customDayMapper: CustomDayMapper

    /**
     * 获取指定CustomDay
     */
    fun getCustomDayById(dayId:Int):SomeDay?{

        val customDay: CustomDayDO? = customDayMapper.findDayId(dayId)

        customDay?.let{
            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
            day.sugIds = formatSugIds(it.suggestions)
            return day
        }

        return null
    }

    private fun formatSugIds(suggestions: String?): List<Int> {
        val idsStr: List<String>? = suggestions?.split(",")

        var ids  = mutableListOf<Int>()
        idsStr?.forEach {
            ids.add(it.toInt())
        }

        return ids

    }

    /**
     * 获取指定用户所有有效CustomDay
     */
    fun getCustomDaysByUserId(userId:Int):List<SomeDay>{
        val customDaySource: MutableList<CustomDayDO>? = customDayMapper.findDayByUserId(userId)

        val dayList = mutableListOf<SomeDay>()
        customDaySource?.forEach{
            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
            day.sugIds = formatSugIds(it.suggestions)
            dayList.add(day)
        }

        return dayList
    }




}