package com.ds.aix.service;

import com.ds.aix.common.result.Result;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
public interface NL2SQLService {

    /**
     * 问答
     *
     * @param question 问题
     * @return
     */
    Result<Object> ask(String question);

}
