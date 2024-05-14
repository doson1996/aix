package com.ds.aix.service.impl;

import com.ds.aix.common.constant.AixConstant;
import com.ds.aix.common.result.Result;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.dao.MongoDao;
import com.ds.aix.exception.BusinessException;
import com.ds.aix.service.NL2SQLService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

            // 机构名
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
