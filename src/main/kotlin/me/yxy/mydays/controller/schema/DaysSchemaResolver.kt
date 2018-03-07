package me.yxy.mydays.controller.schema

import graphql.schema.idl.RuntimeWiring
import me.yxy.mydays.controller.fetcher.DayFetcher
import me.yxy.mydays.controller.fetcher.DaysFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.core.io.ClassPathResource
import java.io.InputStream
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
    lateinit var daysFetcher: DaysFetcher

    @Autowired
    lateinit var dayFetcher: DayFetcher

    override fun buildWiringContent(): RuntimeWiring {

        //这里最好能够熟悉一下lamada，变成spring 注解模式的方式自动装配dataFetcher
        return RuntimeWiring.newRuntimeWiring()
                .type("Query")
                { typeW -> typeW
                        .dataFetcher("days",daysFetcher)
                        .dataFetcher("day",dayFetcher)}
                .build()
    }

}