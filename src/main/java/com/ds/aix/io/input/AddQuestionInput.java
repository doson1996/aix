package com.ds.aix.io.input;

import lombok.Data;

/**
 * @author ds
 * @date 2024/5/13
 * @description
 */
@Data
public class AddQuestionInput {

    /**
     * 问题
     */
    private String question;

    /**
     * 对应解析key
     */
    private String parseKey;

}
