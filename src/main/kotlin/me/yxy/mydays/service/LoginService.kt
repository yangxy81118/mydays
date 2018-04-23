package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.UserDO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

/**
 * 登陆验证服务
 */
@Service
class LoginService {

    @Autowired
    private lateinit var userMapper:UserMapper

    /**
     * 根据token获取用户ID
     */
    fun getUserIdByToken(token:String):Int{
       val result = userMapper.findUserIdByToken(token)
       return result?.id ?: 0
    }

    /**
     * 生成token
     */
    fun createToken(userId: Int):String{
       return getMD5Str(userId.toString() +"YOYOCICILOVE")
    }

    private fun getMD5Str(str: String): String {
        try {
            // 生成一个MD5加密计算摘要
            val md = MessageDigest.getInstance("MD5")
            // 计算md5函数
            md.update(str.toByteArray())
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return BigInteger(1, md.digest()).toString(16)
        } catch (e: Exception) {
            throw Exception("MD5加密出现错误，" + e.toString())
        }

    }

    fun insertTokenForUser(userId: Int) : String {
        val user = UserDO()
        user.id = userId
        val token = createToken(userId)
        user.loginToken = token
        userMapper.updateUser(user)
        return token
    }
}