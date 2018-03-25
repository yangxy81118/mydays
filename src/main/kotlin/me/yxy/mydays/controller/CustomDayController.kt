package me.yxy.mydays.controller

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.service.CustomDayService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * 用户自定义日期控制类，这里只处理修改类请求，查询类请求统一由GraphController处理
 */
@RestController
@RequestMapping("/customDay")
class CustomDayController {

    @Autowired
    private lateinit var customDayService: CustomDayService

    private val logger: Logger = LoggerFactory.getLogger(CustomDayController::class.java)

    @CrossOrigin
    @PutMapping()
    fun addCustomDay(@RequestBody request: AddDay): ResponseEntity<String> {
        customDayService.saveOrUpdateDay(request)
        return ResponseEntity.ok("ok")
    }


    @CrossOrigin
    @PostMapping()
    fun updateCustomDay(@RequestBody request: AddDay): ResponseEntity<String> {
        customDayService.saveOrUpdateDay(request)
        return ResponseEntity.ok("ok")
    }


    @CrossOrigin
    @DeleteMapping()
    fun deleteDay(@RequestParam("dayId") dayId:Int): ResponseEntity<String> {

        logger.info("Delete Day,dayId:$dayId")
        //TODO 这里首先要通过token校验用户的登陆信息，通过token获取到对应的userId，然后userId与dayId进行匹配，不允许操作非用户自身的数据

        customDayService.deleteDay(dayId)
        return ResponseEntity.ok("ok")
    }

}
