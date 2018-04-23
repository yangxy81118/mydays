package me.yxy.mydays.controller

import graphql.ExecutionResult
import me.yxy.mydays.controller.schema.DaysSchemaResolver
import me.yxy.mydays.service.GreetingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


/**
 * GraphQL查询入口，所有GraphQL的相关schema都从这里读取
 */
@RestController
@RequestMapping("/graphql")
class GraphController {

    @Autowired
    lateinit var daysSchemaResolver: DaysSchemaResolver

    private val logger: Logger = LoggerFactory.getLogger(GreetingService::class.java)

    @PostMapping("/days")
    fun daysHanlder(@RequestBody query:String,httpReq:HttpServletRequest): ResponseEntity<ExecutionResult> {
        logger.info("GraphQL:$query")
        val result:ExecutionResult = daysSchemaResolver.graphQL.execute(query)
        return ResponseEntity(result, HttpStatus.OK)
    }

}
