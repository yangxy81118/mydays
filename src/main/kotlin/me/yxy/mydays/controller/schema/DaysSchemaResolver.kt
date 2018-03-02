package me.yxy.mydays.controller.schema

import graphql.schema.idl.RuntimeWiring
import me.yxy.mydays.controller.fetcher.DaysFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File

/**
 * days模块
 */
@Service
class DaysSchemaResolver : BaseSchemaResolver() {

    @Value("classpath:days.graphql")
    lateinit var resource: Resource

    override fun getResourceFile(): File {
        return resource.file
    }

    @Autowired
    lateinit var daysFetcher: DaysFetcher

    override fun buildWiringContent(): RuntimeWiring {

        //这里最好能够熟悉一下lamada，变成spring 注解模式的方式自动装配dataFetcher
        return RuntimeWiring.newRuntimeWiring()
                .type("Query")
                { typeW -> typeW.dataFetcher("days",daysFetcher) }
                .build()

    }

}