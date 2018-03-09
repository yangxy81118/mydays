package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.SuggestionMapper
import me.yxy.mydays.dao.pojo.SuggestionDO
import me.yxy.mydays.service.domain.Suggestion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SuggestionService{

    @Autowired
    private lateinit var suggestionMapper: SuggestionMapper


    /**
     * 根据id获取suggestion列表
     */
    fun getSuggestionByIds(ids:List<Int>):List<Suggestion>{
        val sugs =  suggestionMapper.findByIdList(ids)

        val serviceBeans = mutableListOf<Suggestion>()
        sugs?.let{
            sugs.forEach {
                serviceBeans.add(Suggestion(it.id,it.content,it.image))
            }
        }

        return serviceBeans

    }
}