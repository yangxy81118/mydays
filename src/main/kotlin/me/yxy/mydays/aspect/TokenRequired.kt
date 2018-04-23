package me.yxy.mydays.aspect

/**
 * 标记是否需要进行token校验
 */
@Target(AnnotationTarget.FUNCTION)
annotation class TokenRequired
