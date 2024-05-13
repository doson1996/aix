package com.ds.aix.boot;

import com.ds.aix.common.constant.AixConstant;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ds
 * @date 2024/5/11
 * @description 启动之后，初始化配置
 */
@Slf4j
@Component
public class ApplicationStarted implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化HanLP配置...");
        HanLP.segment("");
        // 初始化词性，避免后续空指针
        CustomDictionary.add("", AixConstant.QUESTION_CONFIG);
        CustomDictionary.add("", AixConstant.COMPANY_CONFIG);
        CustomDictionary.add("", AixConstant.COMPANY_ABBREVIATION_CONFIG);
        log.info("初始化HanLP配置完成...");
    }
}
