package com.zcw.oj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcw.oj.model.dto.question.JudgeConfig;
import com.zcw.oj.model.dto.question.QuestionQueryRequest;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.entity.User;
import com.zcw.oj.model.vo.QuestionVO;
import com.zcw.oj.model.vo.UserVO;
import com.zcw.oj.service.QuestionService;
import com.zcw.oj.mapper.QuestionMapper;
import com.zcw.oj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zcw
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2025-02-23 11:08:32
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Resource
    private UserService userService;


    /**
     * 创建 mp 查询条件
     *
     * @param questionQueryRequest 题目查询请求
     * @return Wrapper<Question>
     */
    @Override
    public Wrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        // 查询的条件
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        String answer = questionQueryRequest.getAnswer();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        // 拼接
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(id), "id", id);
        queryWrapper.eq(ObjectUtil.isNotNull(userId), "userId", userId);
        queryWrapper.like(ObjectUtil.isNotNull(title), "title", title);
        queryWrapper.like(ObjectUtil.isNotNull(content), "content", content);
        queryWrapper.like(ObjectUtil.isNotNull(answer), "answer", answer);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.orderBy(ObjectUtil.isNotNull(sortField), sortOrder.equals("asc"), sortField);
        return queryWrapper;
    }

    /**
     * 获取题目的 VO
     *
     * @param question 题目
     * @param request  请求
     * @return QuestionVO
     */
    @Override
    public QuestionVO getQuestionVO(Question question, HttpServletRequest request) {
        QuestionVO questionVO = new QuestionVO();
        BeanUtil.copyProperties(question, questionVO, CopyOptions.create().setIgnoreError(true));
        // 转换标签类型
        String tagsJson = question.getTags();
        List<String> tags = JSONUtil.toList(tagsJson, String.class);
        questionVO.setTags(tags);
        // 转换判题配置
        String judgeConfigJson = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigJson, JudgeConfig.class);
        questionVO.setJudgeConfig(judgeConfig);
        // 1. 关联绑定用户信息
        Long userId = question.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }else {
            return questionVO;
        }
        UserVO userVO = userService.getUserVO(user);
        questionVO.setUserVO(userVO);
        return questionVO;
    }

    /**
     * 分页对象转换VO
     *
     * @param questionPage
     * @param request
     * @return
     */
    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> records = questionPage.getRecords();
        // 创建新的分页对象
        Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollUtil.isEmpty(records)) {
            return questionVOPage;
        }
        // 1. 关联用户信息
        // 将所有用户id存储在set集合，避免重复查询
        Set<Long> userIdSet = records.stream().map(Question::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        List<QuestionVO> questionVOList = records.stream().map(question -> {
            QuestionVO questionVO = QuestionVO.objToVo(question);
            Long userId = question.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            questionVO.setUserVO(userService.getUserVO(user));
            return questionVO;
        }).collect(Collectors.toList());
        questionVOPage.setRecords(questionVOList);
        return questionVOPage;
    }
}




