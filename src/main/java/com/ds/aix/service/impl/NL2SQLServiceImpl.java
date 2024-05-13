package com.ds.aix.service.impl;

import com.ds.aix.common.constant.AixConstant;
import com.ds.aix.common.result.Result;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.dao.MongoDao;
import com.ds.aix.exception.BusinessException;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;
import com.ds.aix.service.NL2SQLService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@Slf4j
@Service
public class NL2SQLServiceImpl implements NL2SQLService {

    @Resource
    private MongoDao mongoDao;

    @Override
    public Result<Object> ask(String question) {
        if (StringUtils.isBlank(question)) {
            throw new BusinessException("问题不能为空");
        }

        // 解析问题
        List<Term> perceptronResult = perceptron(question);

        // 解析出的公司名集合
        List<String> companyList = new ArrayList<>();

        // 解析出的问题集合
        List<String> questionList = new ArrayList<>();

        for (Term term : perceptronResult) {
            // 解析出的公司名
            String company = "";
            // 是否解析出配置公司名
            boolean configCompanyFlag = false;
            // 解析出的公司简称
            String abbreviation = "";
            // 是否解析出配置公司简称
            boolean configAbbreviationFlag = false;
            // 解析出的地名
            String ns = "";

            // 配置的公司名
            if (Nature.fromString(AixConstant.COMPANY).equals(term.nature)) {
                company = term.word;
                // 是否解析出配置公司名置为true
                configCompanyFlag = true;
            }

            // 配置的公司简称
            if (Nature.fromString(AixConstant.COMPANY_ABBREVIATION).equals(term.nature)) {
                // 如果没有解析出配置的公司名，使用解析出的机构名
                if (!configCompanyFlag) {
                    abbreviation = term.word;
                    // 是否解析出配置公司名置为true
                    configAbbreviationFlag = true;
                }
            }

            // 配置的问题
            if (Nature.fromString(AixConstant.QUESTION).equals(term.nature)) {
                questionList.add(term.word);
            }

            // 机构名  todo 设置的简称 优先级不如 nt？ 重庆银行？
            if (Nature.nt.equals(term.nature)
                    || Nature.ntc.equals(term.nature)
                    || Nature.ntcb.equals(term.nature)) {
                // 如果没有解析出配置的公司名，使用解析出的机构名
                if (!configCompanyFlag) {
                    // 解析出的机构名
                    String org = term.word;
                    // 先使用机构作为简称查询公司名，如果有则使用查询出的公司名
                    String companyName = mongoDao.qryCompanyName(org);
                    if (StringUtils.isNotBlank(companyName)) {
                        company = companyName;
                    } else {
                        // todo 查出来的机构名还需处理
                        company = org;
                    }
                }
            }

            // 地名 （和机构名组合使用，如果机构名为公司名查不出数据，使用 地名+机构名 ）
//            if (Nature.ns.equals(term.nature)) {
//                ns = term.word;
//            }

            // todo 【都查一遍？】 如果解析出的公司名不是配置的，并且公司名不以解析出的地名开头，将地名加上   & 结合 hive？
//            if (!configCompanyFlag && StringUtils.isNotBlank(company) && !company.startsWith(ns)) {
//                company = ns + company;
//            }
            // 如果解析到简称，去mongo查询公司名
            if (!configCompanyFlag && configAbbreviationFlag) {
                company = mongoDao.qryCompanyName(abbreviation);
            }

            // 添加到公司名集合
            if (StringUtils.isNotBlank(company)) {
                companyList.add(company);
            }
        }

        return Result.okData("公司：" + companyList + " 问题：" + questionList);
    }

    @Override
    public Result<Object> addQuestion(AddQuestionInput input) {
        String question = input.getQuestion();
        String parseKey = input.getParseKey();

        if (StringUtils.isBlank(question) || StringUtils.isBlank(parseKey)) {
            throw new IllegalArgumentException("question或parseKey不能为空");
        }

        if (CustomDictionary.contains(question)) {
            return Result.ok("当前问题已在问题库中");
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

        if (CustomDictionary.contains(company)) {
            return Result.ok("当前公司已在公司库中");
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

    /**
     * 感知机
     *
     * @param question
     * @return
     */
    private List<Term> perceptron(String question) {
        return seg("perceptron", question);
    }

    /**
     * 维特比
     *
     * @param question
     * @return
     */
    private List<Term> viterbi(String question) {
        return seg("viterbi", question);
    }

    private List<Term> seg(String algorithm, String question) {
        List<Term> result = HanLP.newSegment(algorithm).enableOrganizationRecognize(true).seg(question);
        log.info("问题：{}, {}解析结果：{}", question, algorithm, result);
        return result;
    }

}
