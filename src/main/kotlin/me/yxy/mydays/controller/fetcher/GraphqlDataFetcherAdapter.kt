package me.yxy.mydays.controller.fetcher

import graphql.language.Field
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.util.ObjectUtils

abstract class GraphqlDataFetcherAdapter<T> : DataFetcher<T> {

    protected fun fieldIsSelected(context:DataFetchingEnvironment,fieldName:String) : Boolean{
       val selectionField:List<Field>? = context.selectionSet.get()[fieldName]
       return ObjectUtils.isEmpty(selectionField)
    }

}