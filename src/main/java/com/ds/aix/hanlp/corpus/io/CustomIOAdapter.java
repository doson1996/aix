package com.ds.aix.hanlp.corpus.io;

import com.ds.aix.dao.MongoDao;
import com.hankcs.hanlp.corpus.io.FileIOAdapter;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ds
 * @date 2024/5/10
 * @description 自定义IO适配器
 */
@Slf4j
@Component
public class CustomIOAdapter extends FileIOAdapter {

    public CustomIOAdapter() {
        log.info("CustomIOAdapter...");
    }

    @Resource
    private MongoDao mongoDao;

    @Value("${biz.init-flag:true}")
    private Boolean initFlag;

    @Value("${biz.custom-file.mongo: D://haizhi//config//data-for-1.7.5//data//dictionary//custom//mongo.txt}")
    private String customFile;

    /**
     * 是否需要初始化
     */
    private static final AtomicBoolean NEED_INIT = new AtomicBoolean(true);

    @Override
    public InputStream open(String path) throws FileNotFoundException {
        try {
            if (initFlag && NEED_INIT.get()) {
                // 只执行一次
                NEED_INIT.set(false);
                log.info("读取mongo配置数据");
                List<Document> list = mongoDao.queryQuestions();
                File file = new File(customFile);
                Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()));
                for (int i = 0; i < list.size(); i++) {
                    Document document = list.get(i);
                    String question = document.getString("question");
                    String line = question + " hzq 1";
                    if (i < list.size() - 1) {
                        line = line + "\r\n";
                    }
                    log.info("读到配置：" + line);
                    writer.write(line);
                }
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("发生异常: " + e);
        }

        return super.open(path);
    }

    @Override
    public OutputStream create(String path) throws FileNotFoundException {
        return super.create(path);
    }

}
