package me.yxy.mydays.controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import me.yxy.mydays.controller.vo.request.UserReq
import me.yxy.mydays.controller.vo.response.LoginResponse
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.UserDO
import me.yxy.mydays.service.LoginService
import me.yxy.mydays.service.UserService
import me.yxy.mydays.tools.URLTool
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*


/**
 * 健康度检查
 */
@RestController
class LoginController {


    val APP_ID:String = "wxefb683378c9b03a9"
    val APP_SECRET:String = "a1c560488dfab1482efbd9c643aff2aa"

    @Autowired
    lateinit var userMapper:UserMapper

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var loginService: LoginService

    private val logger: Logger = LoggerFactory.getLogger(LoginController::class.java)

    @CrossOrigin
    @GetMapping("/login")
    fun queryUserId(@RequestParam(value = "code") code: String): ResponseEntity<LoginResponse> {

        var url = "https://api.weixin.qq.com/sns/jscode2session?appid=$APP_ID&secret=$APP_SECRET&js_code=$code&grant_type=authorization_code"
        val response = URLTool.get(url)
        println("response:$response")

        val gson = Gson()
        val wxResponseVO = gson.fromJson(response, WXSessionResponse::class.java)

        val openId = wxResponseVO.openId
        var loginUser: UserDO? = userMapper.findUserIdByOpenId(openId)

        var userId:Int
        var token: String
        if (loginUser == null) {
            var req = UserReq(0,openId,"","")
            var newUserId = userService.addUser(req)
            token = loginService.insertTokenForUser(newUserId)
            logger.info("欢迎新用户:userId:$newUserId,openId:$openId")
            userId = newUserId
        }else{

            //如果该用户还没有
            token = if(StringUtils.isEmpty(loginUser.loginToken)){
                loginService.insertTokenForUser(loginUser.id)
            }else{
                loginUser.loginToken!!
            }

            loginUser.lastLoginTime = Date()
            userMapper.updateUser(loginUser)
            userId = loginUser.id
        }

        return ResponseEntity.ok(LoginResponse(userId,token))

    }

}

data class WXSessionResponse(@SerializedName("session_key") val sessionKey:String,
                             @SerializedName("expires_in") val expiresIn:String,
                             @SerializedName("openid") val openId:String)
