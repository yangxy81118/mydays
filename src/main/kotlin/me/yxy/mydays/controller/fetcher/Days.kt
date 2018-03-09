package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.vo.SomeDayView
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.HolidayService
import me.yxy.mydays.service.SuggestionService
import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Days对应的查询处理实现类，这里最好调用特定的service，在后期转为RPC调用，所以fetcher相当于frnt层最后一道关
 */
@Repository
class Days : GraphqlDataFetcherAdapter<MutableList<SomeDayView>>() {

    @Autowired
    lateinit var holidayService: HolidayService

    @Autowired
    lateinit var customDayService: CustomDayService

    @Autowired
    lateinit var suggestionService: SuggestionService

    override fun get(environment: DataFetchingEnvironment):MutableList<SomeDayView> {
        val userId:Int = environment.getArgument<Int>("userId")

        //Holidays
        val dayViews = mutableListOf<SomeDayView>()

        val holidaySource = holidayService.getAllHolidays()

        holidaySource.forEach{
            var viewItem = SomeDayView(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
            val isInFuture:Boolean = findRemainDays(viewItem)
            if(isInFuture) {
                dayViews.add(viewItem)
            }
        }

        userId?.let{

            val customDays = customDayService.getCustomDaysByUserId(userId)
            customDays.forEach{
                var viewItem = SomeDayView(it.id,it.name,it.year,it.month,it.date,it.image,it.engName,it.brief,it.lunar)
                val isInFuture:Boolean = findRemainDays(viewItem)
                if(isInFuture){
                    viewItem.custom = true
                    dayViews.add(viewItem)
                }
            }

        }

        //Sort
        dayViews.sortWith(Comparator { firstOne, secondOne -> firstOne.remain - secondOne.remain})

        return dayViews
    }


    //TODO 当天的判断是有bug的
    private fun findRemainDays(viewItem: SomeDayView) : Boolean {

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