package me.yxy.mydays.service

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.service.domain.SomeDay
import me.yxy.mydays.tools.ChineseCalendar
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


    /**
     * 添加或更新，这里暂时偷懒采用controller中的request，严格来说要使用service层的request
     */
    fun saveOrUpdate(addDayReq:AddDay){

        //拷贝基本属性
        val daoRequest = CustomDayDO()
        daoRequest.name = addDayReq.title
        daoRequest.userId = addDayReq.userId

        //日期处理
        var (year,month,date) = parseDate(addDayReq.date)
        var lunarCalendar:ChineseCalendar? = null
        //如果使用农历
        if(addDayReq.useLunar){
            lunarCalendar = ChineseCalendar(true,year, month, date)
        }else{
            //使用阳历
            lunarCalendar = ChineseCalendar(year,month-1,date)
        }
        val lunarComment:String = lunarCalendar.toString().split(" | ").get(2)

        //年循环判断
        year = if (addDayReq.cycle) 0 else year

        //存储
        daoRequest.year = year
        daoRequest.month = month
        daoRequest.date = date
        daoRequest.lunar = lunarComment
        daoRequest.image = "http://oyu60t3s9.bkt.clouddn.com/keyboard.jpg?imageView2/1/w/100/h/100/q/75|imageslim"

        customDayMapper.addOne(daoRequest)

    }

    private fun parseDate(dayStr: String): TimeParseResult {
        val strArray = dayStr.split("-")
        return TimeParseResult(strArray[0].toInt(),strArray[1].toInt(),strArray[2].toInt())
    }



}

data class TimeParseResult(val year:Int = 0,val month:Int = 0,val date:Int = 0)