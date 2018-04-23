package me.yxy.mydays.configs

import me.yxy.mydays.aspect.TokenFitler
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FilterConfiguration{

    @Bean
    fun tokenFilterRegistration(): FilterRegistrationBean {

        val registration = FilterRegistrationBean()
        registration.filter = TokenFitler()
        registration.addUrlPatterns("/customDays/*")
        registration.setName("tokenFilter")
        registration.order = 1
        return registration
    }

}

