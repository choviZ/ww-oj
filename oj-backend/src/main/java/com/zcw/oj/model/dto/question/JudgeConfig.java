package com.zcw.oj.model.dto.question;

import lombok.Data;

/**
 * 判题配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制
     */
    private Long stackLimit;

}
