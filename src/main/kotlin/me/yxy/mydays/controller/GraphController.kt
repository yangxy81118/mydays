package me.yxy.mydays.controller

import graphql.ExecutionResult
import me.yxy.mydays.controller.schema.DaysSchemaResolver
import me.yxy.mydays.controller.schema.ResourceSchemaResolver
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

    @Autowired
    lateinit var resourceSchemaResolver: ResourceSchemaResolver

    private val logger: Logger = LoggerFactory.getLogger(GraphController::class.java)

    @PostMapping("/days")
    fun daysHanlder(@RequestBody query:String,httpReq:HttpServletRequest): ResponseEntity<Any> {
        logger.info("GraphQL:$query")
        UserIdThreadLocalContainer.container.set(getUserIdFromContext(httpReq))

        val result:ExecutionResult = daysSchemaResolver.graphQL.execute(query)
        return if(result.errors.size > 0){
            ResponseEntity.ok("系统异常")
        }else{
            ResponseEntity.ok(result)
        }
    }

    @PostMapping("/resource")
    fun resourceHanlder(@RequestBody query:String,httpReq:HttpServletRequest): ResponseEntity<Any> {

        logger.info("GraphQL:$query")

        val result:ExecutionResult = resourceSchemaResolver.graphQL.execute(query)
        return if(result.errors.size > 0){
            ResponseEntity.ok("系统异常")
        }else{
            ResponseEntity.ok(result)
        }

    }
    private fun getUserIdFromContext(httpReq: HttpServletRequest): Int {
        return httpReq.getAttribute("userId").toString().toInt()!!
    }
}
