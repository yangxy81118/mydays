package me.yxy.mydays.controller.fetcher

import graphql.schema.DataFetchingEnvironment
import me.yxy.mydays.controller.vo.response.NoticeView
import me.yxy.mydays.service.NoticeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Notice
 */
@Repository
class NoticeFetcher : GraphqlDataFetcherAdapter<List<NoticeView>>() {

    @Autowired
    lateinit var noticeService: NoticeService

    override fun get(environment: DataFetchingEnvironment): List<NoticeView> {

        var page:Int = environment.getArgument<Int>("page")?:1
        page = if(page <= 0 ) 1 else page

        val rows:Int = environment.getArgument<Int>("rows")?:10

        val startRow = (page-1)*rows

        val notices = noticeService.queryNotices(startRow,rows)

        val views = mutableListOf<NoticeView>()
        notices.forEach {
            val view = NoticeView(it.title,it.type)
            view.content = splitToList(it.content)
            views.add(view)
        }

        return views

    }

    private fun splitToList(content: String): List<String>{
        return content.split("|")
    }


}