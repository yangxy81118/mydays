package me.yxy.mydays.service


/**
 * 业务异常
 */
class ServiceException(msg:String, code:Int):RuntimeException(msg) {

    companion object {
        const val UNKNOWN_ERROR = 999
        const val NO_SPACE = 10
    }

    var errorCode = 0
    init{
        errorCode = code
    }
}
