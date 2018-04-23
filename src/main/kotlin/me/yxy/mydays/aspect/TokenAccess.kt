package me.yxy.mydays.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

/**
 * Token校验拦截器
 */
@Aspect
@Component
class TokenAccess{

//    @Before("@annotation(me.yxy.mydays.aspect.TokenRequired)")
//    fun login(pjp:ProceedingJoinPoint){
//        println("check")
//    }
}