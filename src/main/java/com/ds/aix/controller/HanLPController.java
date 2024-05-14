package com.ds.aix.controller;

import com.ds.aix.common.result.Result;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;
import com.ds.aix.service.HanLPService;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ds
 * @date 2024/5/11
 * @description HanLP相关配置
 */
@Slf4j
@Api(tags = "HanLP相关配置")
@RestController
@RequestMapping("hanlp")
public class HanLPController {

    @Resource
    private HanLPService hanLpService;

    @ApiOperation("添加问题")
    @PostMapping("add-question")
    public Result<Object> addQuestion(@RequestBody AddQuestionInput input) {
        Result<Object> result = hanLpService.addQuestion(input);
        return result;
    }

    @ApiOperation("删除问题")
    @PostMapping("del-question")
    public Result<Object> delQuestion(@RequestBody DelQuestionInput input) {
        Result<Object> result = hanLpService.delQuestion(input);
        return result;
    }

    @ApiOperation("添加公司")
    @PostMapping("add-company")
    public Result<Object> addCompany(@RequestBody AddCompanyInput input) {
        Result<Object> result = hanLpService.addCompany(input);
        return result;
    }

    @ApiOperation("删除公司")
    @PostMapping("del-company")
    public Result<Object> delCompany(@RequestBody DelCompanyInput input) {
        Result<Object> result = hanLpService.delCompany(input);
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
