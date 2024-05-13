package com.ds.aix.boot;

import com.ds.aix.common.constant.AixConstant;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ds
 * @date 2024/5/11
 * @description 启动之后，初始化配置
 */
@Slf4j
@Component
public class ApplicationStarted implements ApplicationRunner {

    private static final Set<String> NATURE_SET = new HashSet<>();

    static {
        NATURE_SET.add(AixConstant.QUESTION);
        NATURE_SET.add(AixConstant.COMPANY);
        NATURE_SET.add(AixConstant.COMPANY_ABBREVIATION);
        NATURE_SET.add(AixConstant.QUESTION_CONFIG);
        NATURE_SET.add(AixConstant.COMPANY_CONFIG);
        NATURE_SET.add(AixConstant.COMPANY_ABBREVIATION_CONFIG);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化HanLP配置...");
        HanLP.segment("");
        // 初始化词性，避免后续空指针
        for (String nature : NATURE_SET) {
            if (Nature.fromString(nature) == null) {
                Nature.create(nature);
            }
        }

        log.info("初始化HanLP配置完成...");
    }
}
