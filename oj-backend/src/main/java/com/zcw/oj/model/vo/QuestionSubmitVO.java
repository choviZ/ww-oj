package com.zcw.oj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zcw.oj.model.dto.question.JudgeCase;
import com.zcw.oj.model.dto.question.JudgeConfig;
import com.zcw.oj.model.dto.questionsubmit.JudgeInfo;
import com.zcw.oj.model.entity.Question;
import com.zcw.oj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 提交题目封装类
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return QuestionSubmit对象
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        // 处理json字符串
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return questionVo
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionVo = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionVo);
        // 处理json字符串
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        JudgeInfo judgeInfo = JSONUtil.toBean(judgeInfoStr, JudgeInfo.class);
        questionVo.setJudgeInfo(judgeInfo);
        return questionVo;
    }
}