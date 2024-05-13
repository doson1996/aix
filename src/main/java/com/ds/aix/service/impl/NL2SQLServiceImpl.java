package com.ds.aix.service.impl;

import com.ds.aix.common.constant.AixNatureConstant;
import com.ds.aix.common.result.Result;
import com.ds.aix.common.util.StringUtils;
import com.ds.aix.exception.BusinessException;
import com.ds.aix.hanlp.corpus.tag.AixNature;
import com.ds.aix.service.NL2SQLService;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@Slf4j
@Service
public class NL2SQLServiceImpl implements NL2SQLService {

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
            if (Nature.fromString(AixNatureConstant.COMPANY).equals(term.nature)) {
                company = term.word;
                // 是否解析出配置公司名置为true
                configCompanyFlag = true;
            }

            // 配置的问题
            if (Nature.fromString(AixNatureConstant.QUESTION).equals(term.nature)) {
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

        return Result.ok("公司：" + company + " 问题：" + qKey);
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
