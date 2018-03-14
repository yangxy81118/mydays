package me.yxy.mydays.controller.schema

import graphql.schema.idl.RuntimeWiring
import me.yxy.mydays.controller.fetcher.Day
import me.yxy.mydays.controller.fetcher.Days
import me.yxy.mydays.controller.fetcher.DefaultImages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.core.io.ClassPathResource
import java.io.InputStreamReader
import java.io.Reader


/**
 * days模块
 */
@Service
class DaysSchemaResolver : BaseSchemaResolver() {

    override fun getResourceFile(): Reader {
        val resource = ClassPathResource("days.graphql")
        return InputStreamReader(resource.inputStream)
    }

    @Autowired
    lateinit var daysFetcher: Days

    @Autowired
    lateinit var dayFetcher: Day

    @Autowired
    lateinit var defaultImageFetcher:DefaultImages

    override fun buildWiringContent(): RuntimeWiring {

        //这里最好能够熟悉一下lamada，变成spring 注解模式的方式自动装配dataFetcher
        return RuntimeWiring.newRuntimeWiring()
                .type("Query")
                { typeW -> typeW
                        .dataFetcher("days",daysFetcher)
                        .dataFetcher("day",dayFetcher)
                        .dataFetcher("defaultImages",defaultImageFetcher)}
                .build()
    }

}