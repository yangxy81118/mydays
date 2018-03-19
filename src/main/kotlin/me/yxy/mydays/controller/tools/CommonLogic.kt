package me.yxy.mydays.controller.tools

import me.yxy.mydays.controller.vo.response.SomeDayView
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
        var yearOfNextTargetDay:Int = now.year

        val birthYear = viewItem.year

        val dayTemp = DateTime(yearOfNextTargetDay,viewItem.month,viewItem.date,0,0,0)

        if(dayTemp.isBeforeNow) yearOfNextTargetDay++

        val dayTime = DateTime(yearOfNextTargetDay,viewItem.month,viewItem.date,0,0,0)
        viewItem.remain = Days.daysBetween(now,dayTime).days
        viewItem.age =  yearOfNextTargetDay - birthYear

        if(viewItem.remain < 0){
            return false
        }

        if(viewItem.remain > 365) viewItem.remain -= 365

        return true
    }
}