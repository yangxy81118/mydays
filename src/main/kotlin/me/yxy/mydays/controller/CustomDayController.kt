package me.yxy.mydays.controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.UserDO
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.tools.URLTool
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.util.*


/**
 * 用户自定义日期控制类，这里只处理修改类请求，查询类请求统一由GraphController处理
 */
@RestController
@RequestMapping("/customDay")
class CustomDayController {

    @Autowired
    private lateinit var customDayService: CustomDayService

    @CrossOrigin
    @PutMapping()
    fun addCustomDay(@RequestBody request: AddDay): ResponseEntity<String> {
        customDayService.saveOrUpdate(request)
        return ResponseEntity.ok("ok")
    }

}
