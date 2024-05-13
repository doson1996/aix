package com.ds.aix.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ds
 * @date 2024/5/13
 * @description
 */
@Slf4j
@Component
public class EnvUtils implements EnvironmentAware {

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static String getString(String key) {
        return env.getProperty(key);
    }

}

