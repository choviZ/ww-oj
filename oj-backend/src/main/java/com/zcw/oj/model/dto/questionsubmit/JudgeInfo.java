package com.zcw.oj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 程序执行信时间
     */
    private Long time;

    /**
     * 程序执行消耗内存
     */
    private Long memory;
}
