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

    @Select("SELECT * FROM user WHERE state = 1 AND id = #{id}")
    fun findById(userId:Int): UserDO

    @Insert("""
        INSERT INTO user (openId,limitCount,nickName,avatarUrl) VALUES (#{openId},#{limitCount},#{nickName},#{avatarUrl})
        """)
    @Options(useGeneratedKeys=true, keyProperty="id")
    fun addOne(user: UserDO)

//    @Update("UPDATE user SET lastLoginTime = #{lastLoginTime} WHERE id = #{id}")
    @Update("""
        <script>
             UPDATE user
            <set>
                <if test="nickName!=null">
                    nickName = #{nickName},
                </if>
                <if test="avatarUrl!=null">
                    avatarUrl= #{avatarUrl},
                </if>
            </set>
            WHERE id = #{id}
        </script>
        """)
    fun updateUser(user: UserDO)

}