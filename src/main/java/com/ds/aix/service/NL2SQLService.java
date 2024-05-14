package com.ds.aix.service;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;

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

}
