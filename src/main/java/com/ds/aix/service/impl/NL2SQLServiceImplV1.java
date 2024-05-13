package com.ds.aix.service.impl;

import com.ds.aix.common.constant.AixConstant;
import com.ds.aix.common.result.Result;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.exception.BusinessException;
import com.ds.aix.io.input.AddCompanyInput;
import com.ds.aix.io.input.AddQuestionInput;
import com.ds.aix.io.input.DelCompanyInput;
import com.ds.aix.io.input.DelQuestionInput;
import com.ds.aix.service.NL2SQLService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ds
 * @date 2024/5/11
 * @description 解析出单公司、单问题
 */
@Slf4j
//@Service
public class NL2SQLServiceImplV1 implements NL2SQLService {

    @Override
    public Result<Object> ask(String question) {
        if (StringUtils.isBlank(question)) {
            throw new BusinessException("问题不能为空");
        }

        // 解析问题
        List<Term> perceptronResult = perceptron(question);

        // 解析出的公司名
        String company = "";
        // 是否解析出配置公司名
        boolean configCompanyFlag = false;
        // 解析出的问题
        String qKey = "";
        // 解析出的地名
        String ns = "";
        for (Term term : perceptronResult) {
            // 配置的公司名
            if (Nature.fromString(AixConstant.COMPANY).equals(term.nature)) {
                company = term.word;
                // 是否解析出配置公司名置为true
                configCompanyFlag = true;
            }

            // 配置的问题
            if (Nature.fromString(AixConstant.QUESTION).equals(term.nature)) {
                qKey = term.word;
            }

            // 机构名
            if (Nature.nt.equals(term.nature)
                    || Nature.ntc.equals(term.nature)
                    || Nature.ntcb.equals(term.nature)) {
                // 如果没有解析出配置的公司名，使用解析出的机构名
                if (!configCompanyFlag) {
                    company = term.word;
                }
            }

            // 地名 （和机构名组合使用，如果机构名为公司名查不出数据，使用 地名+机构名 ）
            if (Nature.ns.equals(term.nature)) {
                ns = term.word;
            }
        }

        // todo 【都查一遍？】 如果解析出的公司名不是配置的，并且公司名不以解析出的地名开头，将地名加上
        if (!configCompanyFlag && !company.startsWith(ns) && (!company.contains("(" + ns + ")") || !company.contains("（" + ns + "）"))) {
            company = ns + company;
        }

        return Result.okData("公司：" + company + " 问题：" + qKey);
    }

    @Override
    public Result<Object> addQuestion(AddQuestionInput input) {
        return null;
    }

    @Override
    public Result<Object> addCompany(AddCompanyInput input) {
        return null;
    }

    @Override
    public Result<Object> delQuestion(DelQuestionInput input) {
        return null;
    }

    @Override
    public Result<Object> delCompany(DelCompanyInput input) {
        return null;
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
