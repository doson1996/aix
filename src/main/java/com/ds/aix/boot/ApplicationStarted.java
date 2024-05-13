package com.ds.aix.boot;

import com.hankcs.hanlp.HanLP;
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
        log.info("初始化HanLP配置完成...");
    }
}
