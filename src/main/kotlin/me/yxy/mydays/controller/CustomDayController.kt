package me.yxy.mydays.controller

import me.yxy.mydays.aspect.TokenRequired
import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.controller.vo.response.ActionResponse
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.service.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils.isNotBlank
import org.springframework.util.StringUtils


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
    fun addCustomDay(@RequestBody request: AddDay,httpReq: HttpServletRequest): ResponseEntity<String> {
        logger.info("Add Day : $request")

        // 通过token获取userId，反向检查额度

        var newId:Int? = customDayService.saveOrUpdateDay(request)
        newId?.let {
            return ResponseEntity.ok(newId.toString())
        }
        return ResponseEntity.ok("0")
    }


    @CrossOrigin
    @PutMapping("/byOther")
    fun addDayByOther(@RequestBody request: AddDay,httpReq: HttpServletRequest): ResponseEntity<ActionResponse> {
        logger.info("Add Day By Other: $request")

        //creatorId必须强制为httpReq里的userId
        request.beInviterId = getUserIdFromContext(httpReq)

        var newDayId: Int
        try {
            newDayId = customDayService.saveDayByOther(request)
        }catch (e:ServiceException){
            return ResponseEntity.ok(ActionResponse(e.errorCode,e.message!!))
        }

        return ResponseEntity.ok(ActionResponse(0,newDayId.toString()))
    }


    @CrossOrigin
    @PostMapping("/copy")
    fun copyDay(@RequestBody request: AddDay,httpReq: HttpServletRequest): ResponseEntity<ActionResponse> {

        logger.info("Copy Day : $request")
        request.userId = getUserIdFromContext(httpReq)

        var newDayId = customDayService.copyDay(request.dayId,request.userId)
        return ResponseEntity.ok(ActionResponse(0,newDayId.toString()))
    }

    private fun getUserIdFromContext(httpReq: HttpServletRequest): Int {
        return httpReq.getAttribute("userId").toString().toInt()!!
    }

    @CrossOrigin
    @PostMapping()
    fun updateCustomDay(@RequestBody request: AddDay,httpReq:HttpServletRequest):ResponseEntity<ActionResponse>  {
        logger.info("Update Day : $request")

        //通过token获取userId，校验userId和dayId的归属权！！！！
        val userId:String? = httpReq.getAttribute("userId")?.toString()
        if(!checkOwner(request.dayId,userId)) {
            logger.warn("用户 $userId 正在尝试修改别人的数据，dayId=${request.dayId}")
            return ResponseEntity.ok(ActionResponse(500, "Hey Bro, I'm watching you!!!!  "))
        }

        customDayService.saveOrUpdateDay(request)
        return ResponseEntity.ok(ActionResponse(0,"ok"))
    }



    @TokenRequired
    @CrossOrigin
    @DeleteMapping()
    fun deleteDay(@RequestParam("dayId") dayId:Int,httpReq: HttpServletRequest): ResponseEntity<ActionResponse> {
        logger.info("Delete Day,dayId:$dayId")

        val userId:String? = httpReq.getAttribute("userId")?.toString()
        if(!checkOwner(dayId,userId)){
            logger.warn("用户 $userId 正在尝试修改别人的数据，dayId=$dayId")
            return ResponseEntity.ok(ActionResponse(500,"Hey Bro, I'm watching you!!!!  "))
        }

        //通过token获取userId，校验userId和dayId的归属权！！！！
        customDayService.deleteDay(dayId)
        return ResponseEntity.ok(ActionResponse(0,"ok"))
    }

    private fun checkOwner(dayId: Int, userId:String?): Boolean {

        userId?.let{
            val targetDay = customDayService.getCustomDayById(dayId)
            targetDay?.let {
                return userId.toInt() == targetDay.userId
            }
        }

        return false

    }


}
