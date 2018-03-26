package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.DefaultImagesMapper
import me.yxy.mydays.dao.mapper.SuggestionMapper
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.SuggestionDO
import me.yxy.mydays.service.domain.Suggestion
import me.yxy.mydays.service.domain.User
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 图片服务
 */
@Service
class UserService {

    @Autowired
    private lateinit var userMapper:UserMapper


    /**
     * 获取用户信息
     */
    fun getUserInfo(userId:Int):User{
        val userDO = userMapper.findById(userId)
        val user = User()
        BeanUtils.copyProperties(userDO,user)
        return user
    }
}