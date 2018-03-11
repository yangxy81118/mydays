package me.yxy.mydays.controller.tools

import me.yxy.mydays.controller.vo.SomeDayView
import org.joda.time.DateTime
import org.joda.time.Days


/**
 * 通用逻辑工具类
 */
object CommonLogic {


    /**
     * 检查是否还是未来日期，以及还剩多少天
     */
    fun checkAndfindRemainDays(viewItem: SomeDayView) : Boolean {

        val now: DateTime = DateTime.now()
        var year:Int = now.year

        val dayTemp: DateTime = DateTime(year,viewItem.month,viewItem.date,0,0,0)
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