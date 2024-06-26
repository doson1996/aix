package com.ds.aix.dao.impl;

import com.ds.aix.dao.MongoDao;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
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
        List<Document> result = new ArrayList<>();
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> question = ds.getCollection("nl2sql_question");
            FindIterable<Document> documents = question.find();
            for (Document document : documents) {
                result.add(document);
            }
        } catch (Exception e) {
            log.error("查询问题合集发生异常：", e);
        }
        return result;
    }

    @Override
    public List<Document> queryCompanys() {
        List<Document> result = new ArrayList<>();
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> question = ds.getCollection("nl2sql_company");
            FindIterable<Document> documents = question.find();
            for (Document document : documents) {
                result.add(document);
            }
        } catch (Exception e) {
            log.error("查询公司合集发生异常：", e);
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
            // 存入之前先删除所有匹配的数据
            companyCol.deleteMany(Filters.eq("company", company));
            companyCol.insertOne(company);
            return true;
        } catch (Exception e) {
            log.error("存入mongo公司库发生异常：", e);
        }
        return false;
    }

    @Override
    public boolean delCompany(String company) {
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> companyCol = ds.getCollection("nl2sql_company");
            // 删除所有匹配的数据
            companyCol.deleteMany(Filters.eq("company", company));
            return true;
        } catch (Exception e) {
            log.error("删除mongo公司库发生异常：", e);
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
            MongoCollection<Document> questionCol = ds.getCollection("nl2sql_question");
            // 存入之前先删除所有匹配的数据
            questionCol.deleteMany(Filters.eq("question", question));
            questionCol.insertOne(question);
            return true;
        } catch (Exception e) {
            log.error("存入mongo问题库发生异常：", e);
        }
        return false;
    }

    @Override
    public boolean delQuestion(String question) {
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> questionCol = ds.getCollection("nl2sql_question");
            // 删除所有匹配的数据
            questionCol.deleteMany(Filters.eq("question", question));
            return true;
        } catch (Exception e) {
            log.error("删除mongo问题库发生异常：", e);
        }
        return false;
    }

    @Override
    public boolean abbreviationUsed(String company, String abbreviation) {
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> questionCol = ds.getCollection("nl2sql_company");
            // 使用了这个简称并且公司名不等于company的数据
            Bson filers = Filters.in("abbreviation", abbreviation);
            filers = Filters.and(Filters.ne("company", company), filers);
            long count = questionCol.countDocuments(filers);
            return count > 0;
        } catch (Exception e) {
            log.error("删除mongo问题库发生异常：", e);
        }
        return false;
    }

    @Override
    public String qryCompanyName(String abbreviation) {
        String companyName = "";
        try {
            @Cleanup
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase ds = client.getDatabase("ds");
            // 选择 collection
            MongoCollection<Document> questionCol = ds.getCollection("nl2sql_company");

            // 使用了这个简称并且公司名不等于company的数据
            Bson filers = Filters.in("abbreviation", abbreviation);
            for (Document doc : questionCol.find(filers)) {
                companyName = doc.getString("company");
            }
            return companyName;
        } catch (Exception e) {
            log.error("删除mongo问题库发生异常：", e);
        }
        return companyName;
    }

}
