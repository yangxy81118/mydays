package me.yxy.mydays.aspect

import me.yxy.mydays.service.LoginService
import me.yxy.mydays.tools.TokenBox
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenFitler : Filter {


    @Autowired
    private lateinit var loginService:LoginService

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        if(inWhiteList(request)){
            chain?.doFilter(request,response)
            return
        }

        //通过request获取token
        val token:String? = request?.getParameter("token")
        if(StringUtils.isEmpty(token)){
            response?.writer?.print("Hey,bro,it seems something is missing ..hah")
            return
        }

        //去TokenBox中获取userId
        token?.let {
            val userId = loginService.getUserIdByToken(it)
            if(userId > 0){
                request?.setAttribute("userId",userId)
                chain?.doFilter(request,response)
            }else{
                response?.writer?.print("Hey,bro,it seems something is missing ..hah")
            }
        }

    }

    private fun inWhiteList(request: ServletRequest?): Boolean{
        val httpReq : HttpServletRequest = request as HttpServletRequest
        return httpReq.servletPath.toString().contains("login")
    }

    override fun init(p0: FilterConfig?) {

    }

    override fun destroy() {

    }


}