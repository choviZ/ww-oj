package com.zcw.oj.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcw.oj.common.BaseResponse;
import com.zcw.oj.common.ErrorCode;
import com.zcw.oj.common.ResultUtils;
import com.zcw.oj.exception.ThrowUtils;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zcw.oj.model.entity.QuestionSubmit;
import com.zcw.oj.model.entity.User;
import com.zcw.oj.model.vo.QuestionSubmitVO;
import com.zcw.oj.service.QuestionSubmitService;
import com.zcw.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 提交相关接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private UserService userService;


    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @return 提交记录id
     */
    @PostMapping
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() < 0, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        Long id = questionSubmitService.doSubmitQuestion(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(id);
    }

    /**
     * 查询提交记录分页数据包装类
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(questionSubmitQueryRequest == null || questionSubmitQueryRequest.getQuestionId() < 0, ErrorCode.PARAMS_ERROR);
        // 分页查询
        int current = questionSubmitQueryRequest.getCurrent();
        int pageSize = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> page = new Page<>(current, pageSize);
        Wrapper<QuestionSubmit> queryWrapper = questionSubmitService.getQueryWrapper(questionSubmitQueryRequest);
        Page<QuestionSubmit> submitPage = questionSubmitService.page(page, queryWrapper);
        // 数据转换
        Page<QuestionSubmitVO> voPage = questionSubmitService.getQuestionSubmitVOPage(submitPage, request);
        return ResultUtils.success(voPage);
    }
}
