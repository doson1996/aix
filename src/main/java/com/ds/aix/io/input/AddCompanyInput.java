package com.ds.aix.io.input;

import lombok.Data;

import java.util.List;

/**
 * @author ds
 * @date 2024/5/13
 * @description 添加公司入参
 */
@Data
public class AddCompanyInput {

    /**
     * 公司名
     */
    private String company;

    /**
     * 简称
     */
    private List<String> abbreviation;

}
