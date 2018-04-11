package me.yxy.mydays.controller

import me.yxy.mydays.controller.vo.request.UserReq
import me.yxy.mydays.controller.vo.response.ActionResponse
import me.yxy.mydays.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * 用户变更接口
 */
@RestController
@RequestMapping("/user")
class UserController {


    @Autowired
    lateinit var userService:UserService

    @CrossOrigin
    @PutMapping()
    fun addUser(@RequestBody userReq: UserReq):ResponseEntity<ActionResponse> {
        userService.addUser(userReq)
        return ResponseEntity.ok(ActionResponse())
    }


    @CrossOrigin
    @PostMapping()
    fun updateUser(@RequestBody userReq: UserReq):ResponseEntity<ActionResponse> {
        userService.updateUser(userReq)
        return ResponseEntity.ok(ActionResponse())
    }

}

