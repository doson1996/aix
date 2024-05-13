package com.ds.aix.dao.impl;

import com.ds.aix.dao.MongoDao;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Cleanup;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ds
 * @date 2024/5/11
 * @description
 */
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

}
