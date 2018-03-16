package me.yxy.mydays.controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import me.yxy.mydays.controller.vo.request.AddDay
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.UserDO
import me.yxy.mydays.service.CustomDayService
import me.yxy.mydays.tools.CNCalendarStorage
import me.yxy.mydays.tools.ChineseYear
import me.yxy.mydays.tools.URLTool
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.util.*


/**
 * 由于一般查询都用在graphql，所以这里处理一些的简单查询
 */
@RestController
@RequestMapping("/simple-query")
class SimpleQueryController {

    @Autowired
    private lateinit var calanderStorage:CNCalendarStorage

    @CrossOrigin
    @GetMapping("/lunar")
    fun getChineseCalendar():ResponseEntity<List<ChineseYear>>{
        return ResponseEntity.ok(calanderStorage.yearList)
    }

}
