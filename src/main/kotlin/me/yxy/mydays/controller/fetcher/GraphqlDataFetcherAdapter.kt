package me.yxy.mydays.controller.fetcher

import graphql.language.Field
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.CustomDayController
import me.yxy.mydays.controller.UserIdThreadLocalContainer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ObjectUtils

abstract class GraphqlDataFetcherAdapter<T> : DataFetcher<T> {

    private val logger: Logger = LoggerFactory.getLogger(GraphqlDataFetcherAdapter::class.java)

    protected fun fieldIsSelected(context:DataFetchingEnvironment,fieldName:String) : Boolean{
       val selectionField:List<Field>? = context.selectionSet.get()[fieldName]
       return !ObjectUtils.isEmpty(selectionField)
    }


    protected fun illegalOwner(targetUserId: Int) : Boolean {
        val realUserId = UserIdThreadLocalContainer.container.get()
        return if(targetUserId != realUserId){
           logger.warn("用户$realUserId 试图查询用户 $targetUserId 的数据!")
            true
        }else{
           false
        }
    }

}