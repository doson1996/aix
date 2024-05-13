package com.ds.aix.controller;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.service.NL2SQLService;
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

    @ApiOperation("添加问题")
    @PostMapping("add-question")
    public Result<Object> addQuestion(@RequestBody AddQuestionInput input) {
        Result<Object> result = nl2SqlService.addQuestion(input);
        return result;
    }

    @ApiOperation("添加公司")
    @PostMapping("add-company")
    public Result<Object> addCompany(@RequestBody AddCompanyInput input) {
        Result<Object> result = nl2SqlService.addCompany(input);
        return result;
    }

}
