package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.vo.SomeDayView
import me.yxy.mydays.service.DayService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Day对应的查询处理实现类，这里最好调用特定的service，在后期转为RPC调用，所以fetcher相当于frnt层最后一道关
 */
@Repository
class DayFetcher : DataFetcher<SomeDayView> {

    @Autowired
    lateinit var dayService:DayService

    override fun get(environment: DataFetchingEnvironment):SomeDayView? {
        return dayService.getSomeDayById(environment.getArgument<Int>("dayId"))
    }


}