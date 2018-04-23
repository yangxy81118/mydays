package me.yxy.mydays.controller.vo.response

/**
 * 操作结果返回
 * @param msg 错误异常
 * @param code 错误代码，非0均为错误
 */
data class ActionResponse(var code:Int = 0,var msg:String = "")