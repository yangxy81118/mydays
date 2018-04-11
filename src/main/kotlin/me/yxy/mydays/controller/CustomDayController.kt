package me.yxy.mydays.controller

import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.controller.vo.response.ActionResponse
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.xml.ws.Action
import javax.xml.ws.Service


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
        logger.info("Add Day : $request")
        var newId:Int? = customDayService.saveOrUpdateDay(request)
        newId?.let {
            return ResponseEntity.ok(newId.toString())
        }
        return ResponseEntity.ok("0")
    }


    @CrossOrigin
    @PutMapping("/byOther")
    fun addDayByOther(@RequestBody request: AddDay): ResponseEntity<ActionResponse> {
        logger.info("Add Day By Other: $request")
        var newDayId: Int = 0
        try {
            newDayId = customDayService.saveDayByOther(request)
        }catch (e:ServiceException){
            return ResponseEntity.ok(ActionResponse(e.errorCode,e.message!!))
        }

        return ResponseEntity.ok(ActionResponse(0,newDayId.toString()))
    }


    @CrossOrigin
    @PostMapping("/copy")
    fun copyDay(@RequestBody request: AddDay): ResponseEntity<ActionResponse> {
        logger.info("Copy Day : $request")
        var newDayId = customDayService.copyDay(request.dayId,request.userId)
        return ResponseEntity.ok(ActionResponse(0,newDayId.toString()))
    }

    @CrossOrigin
    @PostMapping()
    fun updateCustomDay(@RequestBody request: AddDay): ResponseEntity<String> {
        logger.info("Update Day : $request")
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
