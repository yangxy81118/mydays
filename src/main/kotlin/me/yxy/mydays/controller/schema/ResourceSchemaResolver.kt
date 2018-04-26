package me.yxy.mydays.controller.schema

import graphql.schema.idl.RuntimeWiring
import me.yxy.mydays.controller.fetcher.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.InputStreamReader
import java.io.Reader


/**
 * resources模块，主要访问一些与具体用户无关的资源数据
 */
@Component
class ResourceSchemaResolver : BaseSchemaResolver() {

    override fun getResourceFile(): Reader {
        val resource = ClassPathResource("resource.graphql")
        return InputStreamReader(resource.inputStream)
    }

    @Autowired
    lateinit var noticeFetcher: NoticeFetcher

    override fun buildWiringContent(): RuntimeWiring {

        return RuntimeWiring.newRuntimeWiring()
                .type("Query")
                { typeW -> typeW
                        .dataFetcher("notice",noticeFetcher)}
                .build()
    }

}