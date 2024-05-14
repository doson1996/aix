package com.ds.aix.controller;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;
import com.ds.aix.service.NL2SQLService;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@Slf4j
@Api(tags = "NL2SQL能力")
@RestController
@RequestMapping("nl2sql")
public class NL2SQLController {

    @Resource
    private NL2SQLService nl2SqlService;

    @ApiOperation("问答")
    @GetMapping("ask")
    public Result<Object> ask(String question) {
        Result<Object> result = nl2SqlService.ask(question);
        log.info("问题: {}, 返回结果: {}", question, result);
        return result;
    }

}
