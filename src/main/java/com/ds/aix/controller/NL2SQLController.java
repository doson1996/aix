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

    @ApiOperation("添加问题")
    @PostMapping("add-question")
    public Result<Object> addQuestion(@RequestBody AddQuestionInput input) {
        Result<Object> result = nl2SqlService.addQuestion(input);
        return result;
    }

    @ApiOperation("删除问题")
    @PostMapping("del-question")
    public Result<Object> delQuestion(@RequestBody DelQuestionInput input) {
        Result<Object> result = nl2SqlService.delQuestion(input);
        return result;
    }

    @ApiOperation("添加公司")
    @PostMapping("add-company")
    public Result<Object> addCompany(@RequestBody AddCompanyInput input) {
        Result<Object> result = nl2SqlService.addCompany(input);
        return result;
    }

    @ApiOperation("删除公司")
    @PostMapping("del-company")
    public Result<Object> delCompany(@RequestBody DelCompanyInput input) {
        Result<Object> result = nl2SqlService.delCompany(input);
        return result;
    }

    @ApiOperation("重置配置")
    @GetMapping("reload")
    public Result<Object> reload() {
        // todo 目前只是重新读取文件, 后续改造成从mongo重新读取写入文件，然后再读取文件
        boolean reload = CustomDictionary.reload();
        return reload ? Result.ok("重置成功!") : Result.fail("重置失败");
    }

}
