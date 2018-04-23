package me.yxy.mydays.tools

import java.math.BigInteger
import java.security.MessageDigest

/**
 * token工具
 */
object TokenBox{

    private val tokenMapping = mutableMapOf<String,String>()

    init {
        tokenMapping.put("XIAOBAOJIMAMAHAOLIHAI","1")
    }

    /**
     * 获取用户本次登陆token
     */
    fun createUserToken(userId:String):String{
        val tokenStr = getMD5Str(userId+"YOYOCICILOVE"+System.currentTimeMillis())
        tokenMapping[tokenStr] = userId
        return tokenStr
    }

    /**
     * 根据token获取用户id
     */
    fun getUserIdByToken(token:String):String?{
       return tokenMapping[token]
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
}

