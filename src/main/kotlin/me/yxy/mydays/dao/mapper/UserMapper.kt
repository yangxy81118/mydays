package me.yxy.mydays.dao.mapper

import me.yxy.mydays.dao.pojo.UserDO
import org.apache.ibatis.annotations.*

/**
 * 用户DAO接口
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE state = 1 AND openId = #{openId}")
    fun findUserIdByOpenId(openId:String): UserDO?

    @Select("SELECT * FROM user WHERE loginToken = #{loginToken}")
    fun findUserIdByToken(token:String): UserDO?

    @Select("SELECT * FROM user WHERE state = 1 AND id = #{id}")
    fun findById(userId:Int): UserDO

    @Insert("""
        INSERT INTO user (openId,limitCount,nickName,avatarUrl,loginToken)
        VALUES
        (#{openId},#{limitCount},#{nickName},#{avatarUrl},#{loginToken})
        """)
    @Options(useGeneratedKeys=true, keyProperty="id")
    fun addOne(user: UserDO)

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
                <if test="loginToken!=null">
                    loginToken = #{loginToken},
                </if>
            </set>
            WHERE id = #{id}
        </script>
        """)
    fun updateUser(user: UserDO)

}