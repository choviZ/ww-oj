package com.zcw.oj.model.dto.questionsubmit;

import com.zcw.oj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 查询提交题目请求
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户id
     */
    private Long userId;


    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 提交状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}