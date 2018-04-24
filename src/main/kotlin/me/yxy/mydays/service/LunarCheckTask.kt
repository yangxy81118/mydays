package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.CustomDayMapper
import me.yxy.mydays.dao.pojo.CustomDayDO
import me.yxy.mydays.tools.CNCalendarStorage
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * 定时处理刚刚过去的农历
 */
@Service
@EnableScheduling
class LunarCheckTask {

    private val logger: Logger = LoggerFactory.getLogger(LunarCheckTask::class.java)

    @Autowired
    private lateinit var customDayMapper:CustomDayMapper

    @Autowired
    private lateinit var calendarStorage:CNCalendarStorage

    @Scheduled(cron = "5 0 0 * * ?")
    fun reload() {

        //首先从数据库里查询“昨日"的农历数据
        logger.info("开始处理昨日可能的农历过期数据")

        val yesterDay = DateTime.now().plusDays(-1)
        val month = yesterDay.monthOfYear
        val day = yesterDay.dayOfMonth

        val nextLunarYear = buildNextLunarYear()

        val lunarDays = customDayMapper.findLunarDaysBySomeDate(month,day)
        val size = lunarDays?.size?:0
        logger.info("一共找到 $size 个目标日期")


        var nextDate = ""
        lunarDays?.get(0)?.let {
            val nextLunarStr = buildNextLunarStr(it,nextLunarYear)
            nextDate =calendarStorage.getNormalDateFromLunarDate(nextLunarStr)
            logger.info("下一个[$nextLunarStr]对应的阳历是$nextDate")
        }

        lunarDays?.forEach {
            updateNextDate(nextDate,it)
        }

        logger.info("农历数据任务处理完毕")

    }

    private fun buildNextLunarYear(): String {
        val nextYear = DateTime.now().year + 1
        return calendarStorage.getLunarYearFromNormal(nextYear)
    }

    private fun updateNextDate(nextDate: String, sourceDay:CustomDayDO ) {

        val array = nextDate.split("-")
        val month = array[1].toInt()
        val day = array[2].toInt()

        sourceDay.month = month
        sourceDay.date = day

        logger.info("准备更新日期为:$sourceDay")
        customDayMapper.updateOne(sourceDay)
    }

    private fun buildNextLunarStr(source: CustomDayDO, nextLunarYear: String): String {

        val bornLunar = source.lunar
        var bornMonthAndDay = bornLunar.substring(bornLunar.indexOf("年")+2)

        //闰月的规范不好统一，暂时采用下一年去掉闰字的做法
        bornMonthAndDay = bornMonthAndDay.replace("闰","")
        return nextLunarYear+bornMonthAndDay
    }

}