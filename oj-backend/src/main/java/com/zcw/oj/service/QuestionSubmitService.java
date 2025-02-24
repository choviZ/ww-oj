package com.zcw.oj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcw.oj.model.dto.question.QuestionQueryRequest;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zcw.oj.model.entity.User;
import com.zcw.oj.model.vo.QuestionSubmitVO;
import com.zcw.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zcw
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2025-02-23 13:14:11
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 题目id
     * @param loginUser                登录用户
     */
    Long doSubmitQuestion(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 创建 mp 查询条件
     * @param questionSubmitQueryRequest 题目提交查询请求
     * @return Wrapper<QuestionSubmit>
     */
    Wrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目的 VO
     * @param questionSubmit 提交的题目
     * @param loginUser 登录的用户
     * @return QuestionVO
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页对象转换VO
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
