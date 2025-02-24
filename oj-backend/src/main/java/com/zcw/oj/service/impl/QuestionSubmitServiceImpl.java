package com.zcw.oj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcw.oj.common.ErrorCode;
import com.zcw.oj.constant.CommonConstant;
import com.zcw.oj.exception.BusinessException;
import com.zcw.oj.exception.ThrowUtils;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zcw.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.entity.QuestionSubmit;
import com.zcw.oj.model.entity.User;
import com.zcw.oj.model.enums.QuestSubmitStatusEnum;
import com.zcw.oj.model.enums.QuestionSubmitLanguageEnum;
import com.zcw.oj.model.vo.QuestionSubmitVO;
import com.zcw.oj.service.QuestionService;
import com.zcw.oj.service.QuestionSubmitService;
import com.zcw.oj.mapper.QuestionSubmitMapper;
import com.zcw.oj.service.UserService;
import com.zcw.oj.utils.SqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 题目id
     * @param loginUser                登录用户
     */
    @Override
    public Long doSubmitQuestion(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 判断编程语言是否合法
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(questionSubmitAddRequest.getLanguage());
        ThrowUtils.throwIf(languageEnum == null, ErrorCode.PARAMS_ERROR, "不支持的编程语言");
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
        // 初始状态
        questionSubmit.setStatus(QuestSubmitStatusEnum.WAITING.getValue());

        boolean result = this.save(questionSubmit);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return questionSubmit.getId();
    }

    /**
     * 获取 queryWrapper
     *
     * @param questionSubmitQueryRequest 题目提交查询请求
     * @return
     */
    @Override
    public Wrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        String language = questionSubmitQueryRequest.getLanguage();
        Long userId = questionSubmitQueryRequest.getUserId();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Integer status = questionSubmitQueryRequest.getStatus();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtil.isNotNull(userId), "userId", userId);
        queryWrapper.eq(ObjectUtil.isNotNull(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtil.isNotNull(status), "status", status);
        // isDelete
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    /**
     * 获取题目的 VO
     *
     * @param questionSubmit 提交的题目
     * @param loginUser      登录的用户
     * @return QuestionVO
     */
    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        // 不是本人或管理员，隐藏提交的代码
        if (questionSubmit.getUserId() != loginUser.getId() || !userService.isAdmin(loginUser)) {
            questionSubmit.setCode(null);
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        return questionSubmitVO;
    }

    /**
     * 分页对象转换VO
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        // 获取分页信息
        long current = questionSubmitPage.getCurrent();
        long size = questionSubmitPage.getSize();
        long total = questionSubmitPage.getTotal();
        List<QuestionSubmit> records = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> voPage = new Page<>(current, size, total);
        // 类型转换
        User loginUser = userService.getLoginUser(request);
        List<QuestionSubmitVO> questionSubmitVOList = records.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = getQuestionSubmitVO(questionSubmit, loginUser);
            return questionSubmitVO;
        }).collect(Collectors.toList());
        BeanUtil.copyProperties(questionSubmitPage, voPage);
        voPage.setRecords(questionSubmitVOList);
        return voPage;
    }
}




