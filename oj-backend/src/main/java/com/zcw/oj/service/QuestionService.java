package com.zcw.oj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zcw.oj.model.dto.question.QuestionQueryRequest;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author zcw
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2025-02-23 11:08:32
*/
public interface QuestionService extends IService<Question> {

    /**
     * 创建 mp 查询条件
     * @param questionQueryRequest 题目查询请求
     * @return Wrapper<Question>
     */
    Wrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目的 VO
     * @param question 题目
     * @param request 请求
     * @return QuestionVO
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页对象转换VO
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
