package me.yxy.mydays.controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.User
import me.yxy.mydays.tools.URLTool
import org.springframework.beans.factory.annotation.Autowired
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

    @CrossOrigin
    @GetMapping("/login")
    fun queryUserId(@RequestParam(value = "code") code: String): Int {

        var url = "https://api.weixin.qq.com/sns/jscode2session?appid=$APP_ID&secret=$APP_SECRET&js_code=$code&grant_type=authorization_code"
        val response = URLTool.get(url)
        println("reponse:$response")

        val gson = Gson()
        val wxResponseVO = gson.fromJson(response, WXSessionResponse::class.java)

        val openId = wxResponseVO.openId
        var loginUser: User? = userMapper.findUserIdByOpenId(openId)
        if (loginUser == null) {
            loginUser = User()
            loginUser.openId = openId
            userMapper.addOne(loginUser)
        }else{
            loginUser.lastLoginTime = Date()
            userMapper.updateUser(loginUser)
        }

        return loginUser.id
    }

}

data class WXSessionResponse(@SerializedName("session_key") val sessionKey:String,
                             @SerializedName("expires_in") val expiresIn:String,
                             @SerializedName("openid") val openId:String)
