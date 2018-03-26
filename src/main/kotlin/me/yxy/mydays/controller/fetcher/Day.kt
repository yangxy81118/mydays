package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.tools.CommonLogic
import me.yxy.mydays.controller.vo.response.SomeDayView
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.HolidayService
import me.yxy.mydays.service.SuggestionService
import me.yxy.mydays.service.domain.SomeDay
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.util.ObjectUtils

/**
 * Day对应的查询处理实现类，这里最好调用特定的service，在后期转为RPC调用，所以fetcher相当于frnt层最后一道关
 */
@Repository
class Day : GraphqlDataFetcherAdapter<SomeDayView>() {

    @Autowired
    lateinit var holidayService:HolidayService

    @Autowired
    lateinit var customDayService:CustomDayService

    @Autowired
    lateinit var suggestionService: SuggestionService


    override fun get(environment: DataFetchingEnvironment): SomeDayView? {

        val dayId:Int = environment.getArgument<Int>("dayId")

        //首先获取Day基本信息
        var day: SomeDay?
        if(dayId < 100000){
            day = holidayService.getHolidayById(dayId)
        }else{
            day = customDayService.getCustomDayById(dayId)
        }

        if(day==null){
            return null
        }

        val dayView = SomeDayView()

        BeanUtils.copyProperties(day,dayView)
        CommonLogic.checkAndfindRemainDays(dayView)

        //监测，如果有需要获取suggestion信息，则往下走一步
        if(!fieldIsSelected(environment,"suggestions")){
            if(!ObjectUtils.isEmpty(day.sugIds)){
                dayView.suggestions  = suggestionService.getSuggestionByIds(day.sugIds!!)
            }
        }

        return dayView

    }


}