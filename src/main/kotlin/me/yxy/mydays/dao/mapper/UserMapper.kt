package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.UserDO
import org.apache.ibatis.annotations.*

/**
 * 节日DAO接口
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE state = 1 AND openId = #{openId}")
    fun findUserIdByOpenId(openId:String): UserDO?

    @Insert("INSERT INTO user (openId) VALUES (#{openId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    fun addOne(user: UserDO)

    @Update("UPDATE user SET lastLoginTime = #{lastLoginTime} WHERE id = #{id}")
    fun updateUser(user: UserDO)

}