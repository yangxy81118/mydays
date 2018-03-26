package me.yxy.mydays.service

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.service.domain.SomeDay
import me.yxy.mydays.tools.CNCalendarStorage
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomDayService {

    @Autowired
    private lateinit var customDayMapper: CustomDayMapper

    @Autowired
    private lateinit var cnCalStorage:CNCalendarStorage

    private val logger:Logger = LoggerFactory.getLogger(CustomDayService::class.java)

    /**
     * 获取指定CustomDay
     */
    fun getCustomDayById(dayId:Int):SomeDay?{

        val customDay: CustomDayDO? = customDayMapper.findDayId(dayId)

        customDay?.let{
            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
            day.sugIds = formatSugIds(it.suggestions)
            day.favor = it.favor == 1
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
    fun getCustomDaysByUserId(request:CustomDayReqeust):List<SomeDay>{
        val customDaySource: MutableList<CustomDayDO>? = customDayMapper.findDayByUserId(request.userId,request.isFavor)

        val dayList = mutableListOf<SomeDay>()
        customDaySource?.forEach{
            var day = SomeDay(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
//            day.sugIds = formatSugIds(it.suggestions)
            day.favor = it.favor == 1
            dayList.add(day)
        }

        return dayList
    }


    /**
     * 添加或更新，这里暂时偷懒采用controller中的request，严格来说要使用service层的request
     */
    fun saveOrUpdateDay(dayReq:AddDay):Int?{

        logger.info("SaveDay,AddDayRequest:{}", dayReq)

        //拷贝基本属性
        val daoRequest = CustomDayDO()
        daoRequest.name = dayReq.name
        daoRequest.userId = dayReq.userId
        daoRequest.favor = if (dayReq.favor) 1 else 0

        //日期处理
        //如果使用农历
        if(dayReq.dateMode==1){
            daoRequest.lunar = dayReq.date

            val pastYearNormalDate = cnCalStorage.getNormalDateFromLunarDate(daoRequest.lunar)

            //由于出生当年对应的阳历会与今年/明年不同，所以获取到的“月，日”，必须按照今年/明年的来处理
            val recentLunarBirthday = getRecentLunarBirthday(dayReq.date)
            val normalDateStr = cnCalStorage.getNormalDateFromLunarDate(recentLunarBirthday)

            //年份要转换回来，因为要算年岁
            val temp = normalDateStr.split("-")
            val finalDateStr = pastYearNormalDate.split("-")[0] + "-"+temp[1]+"-"+temp[2]
            finalDateStr?.let { putNormalDate(daoRequest, it) }
        }else{
            //使用阳历
            putNormalDate(daoRequest, dayReq.date)
        }


        //如果addDayReq有id，则说明是更新
        if(dayReq.dayId > 0){
            daoRequest.id = dayReq.dayId
            logger.info("Ready Update Day to DB;{}",daoRequest)
            customDayMapper.updateOne(daoRequest)
            return null
        }else{
            logger.info("Ready Add Day to DB:{}",daoRequest)
            customDayMapper.addOne(daoRequest)
            return daoRequest.id
        }

    }

    private fun getRecentLunarBirthday(date: String): String {

        val thisYear:Int = DateTime.now().year
        var thisYearLunarStr = ""

        //首先去获取今年的阳历
        cnCalStorage.yearList.forEach {
            if(it.y.startsWith(thisYear.toString())){
                thisYearLunarStr = it.y
            }
        }

        thisYearLunarStr += date.substring(date.indexOf("）")+1)

        val thisYearNormalDate = cnCalStorage.getNormalDateFromLunarDate(thisYearLunarStr)

        //如果今年的日期已经过了今天的，那就直接算明年的了
        if(DateTimeFormat.forPattern("yyyy-MM-dd")
                .parseDateTime(thisYearNormalDate).plusDays(1).isBeforeNow){

            var nextYearLunarStr = ""
            cnCalStorage.yearList.forEach {
                if(it.y.startsWith((thisYear+1).toString())){
                    nextYearLunarStr = it.y
                }
            }

            nextYearLunarStr += date.substring(date.indexOf("）")+1)
            return nextYearLunarStr
        }

        return thisYearLunarStr

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

    fun deleteDay(dayId: Int) {
        customDayMapper.removeOne(dayId)
    }

    fun countForUser(request: CustomDayReqeust): Int {
        val daoRequst = CustomDayDO()
        daoRequst.favor = if (request.isFavor) 1 else 0
        daoRequst.userId = request.userId
        return customDayMapper.countForUser(daoRequst)
    }

}

/** 用户自定义日期通用查询Request */
data class CustomDayReqeust(val userId:Int = 0, val isFavor:Boolean = false)