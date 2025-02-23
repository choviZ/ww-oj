package com.zcw.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcw.oj.common.ErrorCode;
import com.zcw.oj.exception.BusinessException;
import com.zcw.oj.exception.ThrowUtils;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.entity.QuestionSubmit;
import com.zcw.oj.model.entity.User;
import com.zcw.oj.service.QuestionService;
import com.zcw.oj.service.QuestionSubmitService;
import com.zcw.oj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zcw
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2025-02-23 13:14:11
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Override
    public Long doSubmitQuestion(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 判断题目是否存在
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long userId = loginUser.getId();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setLanguage(questionSubmit.getLanguage());
        questionSubmit.setCode(questionSubmit.getCode());
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(0);

        boolean result = this.save(questionSubmit);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return questionSubmit.getId();
    }
}




