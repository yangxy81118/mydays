package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.UserIdThreadLocalContainer
import me.yxy.mydays.controller.tools.CommonLogic
import me.yxy.mydays.controller.vo.response.SomeDayView
import me.yxy.mydays.service.CustomDayReqeust
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
    lateinit var customDayService: CustomDayService

    override fun get(environment: DataFetchingEnvironment):MutableList<SomeDayView> {

        val userId:Int = environment.getArgument<Int>("userId")
        val isFavor:Boolean = environment.getArgument<Boolean>("favor") ?: false

        checkOwner(userId)

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

            val customDays = customDayService.getCustomDaysByUserId(CustomDayReqeust(userId,isFavor))
            customDays.forEach{
                var viewItem = SomeDayView(it.id, it.name, it.year, it.month, it.date, it.image, it.engName, it.brief, it.lunar)
                val isInFuture:Boolean = CommonLogic.checkAndfindRemainDays(viewItem)
                if(isInFuture){
                    viewItem.custom = true
                    viewItem.favor = it.favor
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


}