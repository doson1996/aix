package com.ds.aix.hanlp.corpus.io;

import com.ds.aix.boot.EnvUtils;
import com.ds.aix.boot.SpringContext;
import com.ds.aix.common.constant.AixConstant;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.dao.MongoDao;
import com.hankcs.hanlp.corpus.io.FileIOAdapter;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

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
public class CustomIOAdapter extends FileIOAdapter {

    private final MongoDao mongoDao = SpringContext.getApplicationContext().getBean(MongoDao.class);

    /**
     * 是否需要初始化
     */
    private static final AtomicBoolean NEED_INIT = new AtomicBoolean(true);

    @Override
    public InputStream open(String path) throws FileNotFoundException {
        try {
            if (NEED_INIT.get()) {
                // 只执行一次
                NEED_INIT.set(false);
                log.info("开始读取mongo配置数据...");
                List<Document> questions = mongoDao.queryQuestions();
                String questionFilePath = EnvUtils.getString("aix.custom-file.question");
                File questionFile = new File(questionFilePath);
                Writer questionWriter = new OutputStreamWriter(Files.newOutputStream(questionFile.toPath()));
                for (int i = 0; i < questions.size(); i++) {
                    Document document = questions.get(i);
                    String question = document.getString("question");
                    StringBuilder line = new StringBuilder(question + AixConstant.QUESTION_CONFIG);
                    log.info("读到问题配置：{}", line);
                    if (i < questions.size() - 1) {
                        line.append(AixConstant.LINE_BREAK);
                    }
                    questionWriter.write(line.toString());
                }
                questionWriter.close();

                List<Document> companys = mongoDao.queryCompanys();
                String companyFilePath = EnvUtils.getString("aix.custom-file.company");
                File companyFile = new File(companyFilePath);
                Writer companyWriter = new OutputStreamWriter(Files.newOutputStream(companyFile.toPath()));
                for (int i = 0; i < companys.size(); i++) {
                    Document document = companys.get(i);
                    String company = document.getString("company");
                    StringBuilder line = new StringBuilder(company + AixConstant.COMPANY_CONFIG);

                    // todo 公司简称
                    List<String> abbreviationList = document.getList("abbreviation", String.class);
                    for (String abbreviation : abbreviationList) {
                        line.append(AixConstant.LINE_BREAK).append(abbreviation).append(AixConstant.COMPANY_ABBREVIATION_CONFIG);
                    }

                    log.info("读到公司配置：{}", line);
                    if (i < companys.size() - 1) {
                        line.append(AixConstant.LINE_BREAK);
                    }
                    companyWriter.write(line.toString());
                }
                companyWriter.close();
            }
        } catch (Exception e) {
            log.error("读取mongo配置发生异常: ", e);
        }

        return super.open(path);
    }

    @Override
    public OutputStream create(String path) throws FileNotFoundException {
        return super.create(path);
    }

}
