package com.ds.aix.service.impl;

import com.ds.aix.common.constant.AixConstant;
import com.ds.aix.common.result.Result;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.dao.MongoDao;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;
import com.ds.aix.service.HanLPService;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ds
 * @date 2024/5/14
 * @description
 */
@Slf4j
@Service
public class HanLPServiceImpl implements HanLPService {

    @Resource
    private MongoDao mongoDao;

    @Override
    public Result<Object> addQuestion(AddQuestionInput input) {
        String question = input.getQuestion();
        String parseKey = input.getParseKey();

        if (StringUtils.isBlank(question) || StringUtils.isBlank(parseKey)) {
            throw new IllegalArgumentException("question或parseKey不能为空");
        }

        // 存入mongo
        Document document = new Document();
        document.put("question", question);
        document.put("parseKey", parseKey);
        mongoDao.saveQuestion(document);

        // 实时添加不要带出现频次（例：nt 1），否则会出现匹配不上
        boolean add = CustomDictionary.add(question, AixConstant.QUESTION);
        return add ? Result.ok("添加成功!") : Result.ok("添加失败!");
    }

    @Override
    public Result<Object> addCompany(AddCompanyInput input) {
        // 公司名
        String company = input.getCompany();

        if (StringUtils.isBlank(company)) {
            throw new IllegalArgumentException("公司名不能为空");
        }

        // 简称
        List<String> abbreviation = input.getAbbreviation();
        // 是否需要处理简称
        abbreviation = dealAbbreviation(company, abbreviation);

        // 存入mongo
        Document document = new Document();
        document.put("company", company);
        document.put("abbreviation", abbreviation);
        mongoDao.saveCompany(document);

        // 实时添加不要带出现频次（例：nt 1），否则会出现匹配不上
        boolean add = CustomDictionary.add(company, AixConstant.COMPANY);
        if (add) {
            // 添加公司简称
            for (String jz : abbreviation) {
                // 实时添加不要带出现频次（例：nt 1），否则会出现匹配不上
                CustomDictionary.add(jz, AixConstant.COMPANY_ABBREVIATION);
            }
        }

        return add ? Result.ok("添加成功!") : Result.ok("添加失败!");
    }

    @Override
    public Result<Object> delQuestion(DelQuestionInput input) {
        String question = input.getQuestion();
        if (StringUtils.isBlank(question)) {
            throw new IllegalArgumentException("question不能为空");
        }
        boolean del = mongoDao.delQuestion(question);
        // todo 不支持删除？
        CustomDictionary.remove(question);
        return del ? Result.ok("删除成功!") : Result.ok("删除失败!");
    }

    @Override
    public Result<Object> delCompany(DelCompanyInput input) {
        String company = input.getCompany();
        if (StringUtils.isBlank(company)) {
            throw new IllegalArgumentException("company不能为空");
        }
        boolean del = mongoDao.delCompany(company);
        // todo 不支持删除？
        CustomDictionary.remove(company);
        return del ? Result.ok("删除成功!") : Result.ok("删除失败!");
    }

    /**
     * 处理简称
     *
     * @param company
     * @param abbreviation
     * @return
     */
    private List<String> dealAbbreviation(String company, List<String> abbreviation) {
        // 优先判断股份有限公司
        if (company.contains("股份有限公司")) {
            abbreviation.add(company.replace("股份有限公司", ""));
        } else if (company.contains("有限公司")) {
            abbreviation.add(company.replace("有限公司", ""));
        }

        // 去重返回
        return abbreviation.stream().filter(jz -> siftAbbreviation(company, jz)).distinct().collect(Collectors.toList());
    }

    /**
     * 处理简称
     *
     * @param company
     * @param abbreviation
     * @return
     */
    private boolean siftAbbreviation(String company, String abbreviation) {
        boolean used = mongoDao.abbreviationUsed(company, abbreviation);
        if (used) {
            log.info("【{}】配置的简称【{}】已被使用", company, abbreviation);
        }
        return !used;
    }

}
