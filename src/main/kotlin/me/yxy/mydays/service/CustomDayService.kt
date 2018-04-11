package me.yxy.mydays.service

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.service.domain.SomeDay
import me.yxy.mydays.tools.CNCalendarStorage
import me.yxy.mydays.tools.UserConfiguration
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomDayService {

    @Autowired
    private lateinit var customDayMapper: CustomDayMapper

    @Autowired
    private lateinit var cnCalStorage:CNCalendarStorage

    @Autowired
    private lateinit var userMapper:UserMapper

    @Autowired
    private lateinit var userConfig: UserConfiguration

    private val logger:Logger = LoggerFactory.getLogger(CustomDayService::class.java)

    /**
     * 获取指定CustomDay
     */
    fun getCustomDayById(dayId:Int):SomeDay?{

        val customDay: CustomDayDO? = customDayMapper.findDayId(dayId)

        customDay?.let{
            var day = SomeDay(it.id,it.name,
                    it.year,it.month,it.date,it.image,
                    it.engName,it.brief,it.lunar,null,it.favor==1,customDay.comment)
//            day.sugIds = formatSugIds(it.suggestions)
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
    fun saveOrUpdateDay(dayReq:AddDay):Int{

        //拷贝基本属性
        val daoRequest = CustomDayDO()
        daoRequest.name = dayReq.name
        daoRequest.userId = dayReq.userId
        daoRequest.comment = dayReq.comment
        daoRequest.favor = if (dayReq.favor) 1 else 0

        if(dayReq.beInviterId > 0){
            daoRequest.creatorId = dayReq.beInviterId
        }else{
            daoRequest.creatorId = dayReq.userId
        }


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
            daoRequest.lastModifiedTime = Date()

            logger.info("Ready Update Day to DB;{}",daoRequest)
            customDayMapper.updateOne(daoRequest)
            return dayReq.dayId
        }else{
            logger.info("Ready Add Day to DB:{}",daoRequest)

            customDayMapper.addOne(daoRequest)
            return daoRequest.id
        }

    }

    /**
     * 通过其他用户添加日期
     */
    fun saveDayByOther(dayReq:AddDay):Int{

        //检查是否满额
        if(!checkIfHasSpace(dayReq.userId)){

            //虽然邀请人的满了，但是被邀请人如果是新用户，一样加入该日期到账户里
            checkAndAddDaysToNewUser(dayReq)

            //满额，抛出异常
            throw ServiceException("额度已满",ServiceException.NO_SPACE)
        }

        //邀请人加入信息
        return saveOrUpdateDay(dayReq)

        //检查被邀请人是否是新用户，如果是新用户，则把这条记录也给他添加进来
//        checkAndAddDaysToNewUser(dayReq)

    }

    private fun checkAndAddDaysToNewUser(dayReq:AddDay){
        if(isNewUser(dayReq.beInviterId)){
            dayReq.userId = dayReq.beInviterId
            saveOrUpdateDay(dayReq)
        }
    }

    private fun checkIfHasSpace(userId: Int): Boolean {

        val query = CustomDayDO()
        query.userId = userId

        val count = customDayMapper.countForUser(query)

        val limit = userMapper.findById(userId)?.limitCount
        limit?.let{
            if(count >= it){
                return false
            }
        }

        return true
    }

    private fun isNewUser(beInviterId: Int): Boolean {
        val user = userMapper.findById(beInviterId)
        return user == null
    }


    infix private fun getRecentLunarBirthday(date: String): String {

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

    /**
     * 删除某一天
     */
    fun deleteDay(dayId: Int) {
        customDayMapper.removeOne(dayId)
    }

    /**
     * 计算用户使用额度
     */
    fun countForUser(request: CustomDayReqeust): Int {
        val daoRequst = CustomDayDO()
        daoRequst.favor = if (request.isFavor) 1 else 0
        daoRequst.userId = request.userId
        return customDayMapper.countForUser(daoRequst)
    }

    /**
     * 计算邀请的人（协助加入生日记录的人数）`
     */
    fun countInvited(userId:Int): Int {
        return customDayMapper.countInvited(userId)
    }

    /**
     * 拷贝某一天的信息，只是将归属人换成另外一个人
     */
    fun copyDay(sourceDayId: Int, receiverId: Int) : Int {
        var sourceDay = customDayMapper.findDayId(sourceDayId)

        sourceDay?.let{
            sourceDay.userId = receiverId
            sourceDay.creatorId = receiverId

            customDayMapper.addOne(sourceDay)
            return sourceDay.id
        }

        return 0

    }

}

/** 用户自定义日期通用查询Request */
data class CustomDayReqeust(val userId:Int = 0, val isFavor:Boolean = false)