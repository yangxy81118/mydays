package me.yxy.mydays.service

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.service.domain.SomeDay
import me.yxy.mydays.tools.CNCalendarStorage
import me.yxy.mydays.tools.ChineseCalendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomDayService {

    @Autowired
    private lateinit var customDayMapper: CustomDayMapper

    @Autowired
    private lateinit var cnCalStorage:CNCalendarStorage

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
        daoRequest.name = addDayReq.name
        daoRequest.userId = addDayReq.userId

        //日期处理
        //如果使用农历
        if(addDayReq.dateMode==1){
            daoRequest.lunar = addDayReq.date
            //将农历翻译成阳历
            val normalDateStr = cnCalStorage.getNormalDateFromLunarDate(addDayReq.date)
            normalDateStr?.let { putNormalDate(daoRequest, it) }
        }else{
            //使用阳历
            putNormalDate(daoRequest,addDayReq.date)
        }

        customDayMapper.addOne(daoRequest)

    }

    private fun putNormalDate(daoRequest: CustomDayDO, normalDateStr: String) {

        val strArray = normalDateStr.split("-")
        val y = strArray[0].toInt()
        val m = strArray[1].toInt()
        val d = strArray[2].toInt()
        daoRequest.year = y
        daoRequest.month = m
        daoRequest.date = d
    }

}
