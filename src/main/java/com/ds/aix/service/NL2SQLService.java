package com.ds.aix.service;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
public interface NL2SQLService {

    /**
     * 问答
     * @param question 问题
     * @return
     */
    Result<Object> ask(String question);

    /**
     *  添加问题
     * @param input 入参
     * @return
     */
    Result<Object> addQuestion(AddQuestionInput input);

    /**
     * 添加公司
     * @param input 入参
     */
    Result<Object> addCompany(AddCompanyInput input);

}
