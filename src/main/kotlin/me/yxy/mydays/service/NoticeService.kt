package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.GreetingMapper
import me.yxy.mydays.dao.mapper.NoticeMapper
import me.yxy.mydays.dao.pojo.GreetingDO
import me.yxy.mydays.dao.pojo.NoticeDO
import me.yxy.mydays.tools.UserConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * 公告通知服务
 */
@Service
class NoticeService {

    @Autowired
    private lateinit var noticeMapper: NoticeMapper

    private var greetings = listOf<GreetingDO>()

    private val logger: Logger = LoggerFactory.getLogger(NoticeService::class.java)


    fun queryNotices(startRow:Int,rowCount:Int):MutableList<NoticeDO>{

        val query = NoticeDO()

        val list = noticeMapper.page(query,startRow,rowCount)

        return list

    }

}

