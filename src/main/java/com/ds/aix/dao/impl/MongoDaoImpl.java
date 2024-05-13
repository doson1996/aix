package com.ds.aix.dao.impl;

import com.ds.aix.dao.MongoDao;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
@Slf4j
@Repository
public class MongoDaoImpl implements MongoDao {

    @Override
    public List<Document> queryQuestions() {
        @Cleanup
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase ds = client.getDatabase("ds");

        // 选择 collection
        MongoCollection<Document> question = ds.getCollection("nl2sql_question");

        List<Document> result = new ArrayList<>();
        FindIterable<Document> documents = question.find();
        for (Document document : documents) {
            result.add(document);
        }

        return result;
    }

    @Override
    public boolean saveCompany(Document company) {
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> companyCol = ds.getCollection("nl2sql_company");
            companyCol.insertOne(company);

            return true;
        } catch (Exception e) {
            log.error("存入mongo公司库发生异常：", e);
        }

        return false;
    }

    @Override
    public boolean saveQuestion(Document question) {
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> companyCol = ds.getCollection("nl2sql_question");
            companyCol.insertOne(question);
            return true;
        } catch (Exception e) {
            log.error("存入mongo问题库发生异常：", e);
        }

        return false;
    }

}
