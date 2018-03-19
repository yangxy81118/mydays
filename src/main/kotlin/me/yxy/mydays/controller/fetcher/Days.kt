package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.tools.CommonLogic
import me.yxy.mydays.controller.vo.response.SomeDayView
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.HolidayService
import org.joda.time.DateTime
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

    override fun get(environment: DataFetchingEnvironment):MutableList<SomeDayView> {
        val userId:Int = environment.getArgument<Int>("userId")

        //Holidays
        val dayViews = mutableListOf<SomeDayView>()

//        val holidaySource = holidayService.getAllHolidays()
//
//        holidaySource.forEach{
//            var viewItem = SomeDayView(it.id, it.name, it.year, it.month, it.date, it.image, it.engName, it.brief, it.lunar)
//            val isInFuture:Boolean = CommonLogic.checkAndfindRemainDays(viewItem)
//            if(isInFuture) {
//                dayViews.add(viewItem)
//            }
//        }

        userId?.let{

            val customDays = customDayService.getCustomDaysByUserId(userId)
            customDays.forEach{
                var viewItem = SomeDayView(it.id, it.name, it.year, it.month, it.date, it.image, it.engName, it.brief, it.lunar)
                val isInFuture:Boolean = CommonLogic.checkAndfindRemainDays(viewItem)
                if(isInFuture){
                    viewItem.custom = true
                    viewItem.favor = it.favor
                    findNextAge(viewItem)
                    dayViews.add(viewItem)
                }

                if(!viewItem.lunar.isEmpty()){
                    val l = viewItem.lunar
                    viewItem.lunar = l.substring(l.indexOf("）")+1,l.length)
                }
            }

        }

        //Sort
        dayViews.sortWith(Comparator { firstOne, secondOne -> firstOne.remain - secondOne.remain})

        return dayViews
    }


    private fun findNextAge(viewItem: SomeDayView) {

        var birthYear = viewItem.year

        val dayTemp = DateTime(birthYear,viewItem.month,viewItem.date,0,0, 0)
        dayTemp.plusDays(1) //如果是今天，也是当作“未来”

        if (dayTemp.isBeforeNow) {

        }



    }


}