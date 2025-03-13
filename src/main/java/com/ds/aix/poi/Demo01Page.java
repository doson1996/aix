package com.ds.aix.poi;

import com.aspose.words.Document;

/**
 * @author ds
 * @date 2025/3/13
 * @description 按照分页拆分
 */
public class Demo01Page {

    public static void main(String[] args) throws Exception {
        // 输入的Word文件路径
        String inputFilePath = "D://hy.docx";
        // 输出目录
        String outputDir = "D://docx//";
        Document doc = new Document(inputFilePath);
        int pageCount = doc.getPageCount();
        for (int page = 0; page < pageCount; page++) {
            Document extractedPage = doc.extractPages(page, 1);
            extractedPage.save(outputDir + (page + 1) + ".docx");
        }
    }

}
