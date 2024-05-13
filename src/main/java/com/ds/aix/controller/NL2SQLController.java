package com.ds.aix.controller;

import com.ds.aix.common.result.Result;
import com.ds.aix.service.NL2SQLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
