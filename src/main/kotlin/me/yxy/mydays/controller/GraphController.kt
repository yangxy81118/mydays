package me.yxy.mydays.controller

import graphql.ExecutionResult
import me.yxy.mydays.controller.schema.DaysSchemaResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * 健康度检查
 */
@RestController
@RequestMapping("/graphql")
class GraphController {

    @Autowired
    lateinit var daysSchemaResolver: DaysSchemaResolver

    @PostMapping("/days")
    fun daysHanlder(@RequestBody query:String): ResponseEntity<ExecutionResult> {
        println("GraphQL:$query")
        val result:ExecutionResult = daysSchemaResolver.graphQL.execute(query)
        return ResponseEntity(result, HttpStatus.OK)
    }

}
