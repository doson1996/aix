package com.ds.aix.controller;

import com.ds.aix.common.result.Result;
import com.ds.aix.service.NL2SQLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@Slf4j
@RestController
@RequestMapping("nl2sql")
public class NL2SQLController {

    @Resource
    private NL2SQLService nl2SqlService;

    @GetMapping("ask")
    public Result<Object> ask(String question) {
        Result<Object> result = nl2SqlService.ask(question);
        log.info("问题: {}, 返回结果: {}", question, result);
        return result;
    }

    @GetMapping("add-question")
    public Result<Object> addQuestion(String question) {
        nl2SqlService.addQuestion(question);
        return Result.ok("添加问题成功!");
    }

    @GetMapping("add-company")
    public Result<Object> addCompany(String company) {
        nl2SqlService.addCompany(company);
        return Result.ok("添加问题成功!");
    }

}
