package com.zcw.oj.service;

import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zcw.oj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zcw.oj.model.entity.User;

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
}
