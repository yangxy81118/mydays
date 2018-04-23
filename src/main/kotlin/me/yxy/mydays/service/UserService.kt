package me.yxy.mydays.service

import me.yxy.mydays.controller.vo.request.UserReq
import me.yxy.mydays.dao.mapper.UserMapper
import me.yxy.mydays.dao.pojo.UserDO
import me.yxy.mydays.service.domain.User
import me.yxy.mydays.tools.UserConfiguration
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

    @Autowired
    private lateinit var userConfig: UserConfiguration


    /**
     * 获取用户信息
     */
    fun getUserInfo(userId:Int):User{
        val userDO = userMapper.findById(userId)
        val user = User()
        BeanUtils.copyProperties(userDO,user)
        user.limit = userDO.limitCount
        return user
    }

    /**
     * 添加用户
     * @return 新用户的ID号
     */
    fun addUser(reqReq: UserReq):Int{
        val userDO = UserDO()
        BeanUtils.copyProperties(reqReq,userDO)
        userDO.limitCount = userConfig.daysLimit
        userMapper.addOne(userDO)
        return userDO.id
    }


    fun updateUser(updateReq: UserReq){
        val userDO = UserDO()
        userDO.avatarUrl = updateReq.avatarUrl
        userDO.nickName = updateReq.nickName
        userDO.id = updateReq.id
        userMapper.updateUser(userDO)
    }
}