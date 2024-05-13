package com.ds.aix.service.impl;

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
        // 解析出公司名
        if (StringUtils.isBlank(question)) {
            throw new BusinessException("问题不能为空");
        }

        List<Term> perceptronResult = perceptron(question);
        log.info("解析结果：{}", perceptronResult);
        // constant.NatureType
        String company = "";
        for (Term term : perceptronResult) {
            if (Nature.create("hzc").equals(term.nature)) {

            } else {

            }
        }

        return Result.ok("");
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
