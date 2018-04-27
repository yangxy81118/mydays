package me.yxy.mydays.controller

import me.yxy.mydays.aspect.TokenRequired
import me.yxy.mydays.controller.vo.request.UserReq
import me.yxy.mydays.controller.vo.response.ActionResponse
import me.yxy.mydays.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


/**
 * 用户变更接口
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService:UserService

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @CrossOrigin
    @PutMapping()
    fun addUser(@RequestBody userReq: UserReq,httpReq: HttpServletRequest):ResponseEntity<ActionResponse> {
        userService.addUser(userReq)
        return ResponseEntity.ok(ActionResponse())
    }


    @CrossOrigin
    @PostMapping()
    fun updateUser(@RequestBody userReq: UserReq,httpReq: HttpServletRequest):ResponseEntity<ActionResponse> {

        //userId强制指向自己
        userReq.id = getUserIdFromContext(httpReq)
        userService.updateUser(userReq)
        return ResponseEntity.ok(ActionResponse())
    }


    @CrossOrigin
    @PostMapping("/enter")
    fun updateEnter(httpReq: HttpServletRequest):ResponseEntity<ActionResponse> {
        var userId = getUserIdFromContext(httpReq)
        logger.info("欢迎用户$userId 回来!")
        userService.lastLogin(userId)
        return ResponseEntity.ok(ActionResponse())
    }

    private fun getUserIdFromContext(httpReq: HttpServletRequest): Int {
        return httpReq.getAttribute("userId").toString().toInt()!!
    }
}

