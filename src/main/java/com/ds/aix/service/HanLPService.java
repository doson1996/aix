package com.ds.aix.service;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;

/**
 * @author ds
 * @date 2024/5/14
 * @description
 */
public interface HanLPService {

    /**
     * 添加问题
     *
     * @param input 入参
     * @return
     */
    Result<Object> addQuestion(AddQuestionInput input);

    /**
     * 添加公司
     *
     * @param input 入参
     */
    Result<Object> addCompany(AddCompanyInput input);

    /**
     * 删除问题
     *
     * @param input
     * @return
     */
    Result<Object> delQuestion(DelQuestionInput input);

    /**
     * 删除公司
     *
     * @param input
     * @return
     */
    Result<Object> delCompany(DelCompanyInput input);

}
