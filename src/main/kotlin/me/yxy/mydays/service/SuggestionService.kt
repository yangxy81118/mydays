package me.yxy.mydays.service

import me.yxy.mydays.dao.mapper.SuggestionMapper
import me.yxy.mydays.dao.pojo.SuggestionDO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SuggestionService{

    @Autowired
    lateinit var suggestionMapper: SuggestionMapper

    /**
     * 根据id获取suggestion列表
     * @return 暂时采用pojo，正确的做法应该是单独有一个domain
     */
    fun getSuggestionByIds(ids:List<Int>):List<SuggestionDO>{
        return suggestionMapper.findByIdList(ids)
    }
}