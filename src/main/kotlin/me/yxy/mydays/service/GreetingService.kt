package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.GreetingMapper
import me.yxy.mydays.dao.pojo.GreetingDO
import me.yxy.mydays.tools.UserConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * 智能问候服务
 */
@Service
@EnableScheduling
class GreetingService {

    @Autowired
    private lateinit var greetingMapper:GreetingMapper

    private var greetings = listOf<GreetingDO>()

    private val logger: Logger = LoggerFactory.getLogger(GreetingService::class.java)

    @PostConstruct
    fun init(){
        reload()
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    fun reload() {
        greetings = greetingMapper.findAll()
        logger.info("Greeting Reload Finish, size is ${greetings.size}")
    }


    fun getRandomGreeting():String{
        val idx = (Math.random() * greetings.size).toInt()
        return greetings[idx].content

    }
}