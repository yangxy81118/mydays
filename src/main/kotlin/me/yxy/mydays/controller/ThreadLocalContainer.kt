package me.yxy.mydays.controller

/**
 * 存放每个线程userId的容器
 */
object UserIdThreadLocalContainer{
    val container:ThreadLocal<Int> = ThreadLocal()
}