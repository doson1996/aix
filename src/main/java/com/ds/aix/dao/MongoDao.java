package com.ds.aix.dao;

import org.bson.Document;

import java.util.List;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
public interface MongoDao {

    /**
     * 查询所有nl2sql_question数据
     * @return
     */
    List<Document> queryQuestions();

}
