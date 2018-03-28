package me.yxy.mydays.controller.tools

import me.yxy.mydays.controller.vo.response.SomeDayView
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours


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

        var dayTemp = DateTime(yearOfNextTargetDay,viewItem.month,viewItem.date,0,0,0)
        dayTemp = dayTemp.plusDays(1)
        if(dayTemp.isBeforeNow) yearOfNextTargetDay++

        val dayTime = DateTime(yearOfNextTargetDay,viewItem.month,viewItem.date,0,0,0)
        val hoursOffset = Hours.hoursBetween(now,dayTime).hours
        if(hoursOffset < 0){
            viewItem.remain = -1
        }else{
            viewItem.remain = Days.daysBetween(now,dayTime).days
        }

        viewItem.age =  yearOfNextTargetDay - birthYear

        //-1表示是今天
        if(viewItem.remain < -1){
            return false
        }

//        if(viewItem.remain > 365) viewItem.remain -= 365

        return true
    }
}