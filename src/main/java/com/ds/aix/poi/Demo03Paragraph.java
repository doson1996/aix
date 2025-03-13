package com.ds.aix.poi;

import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import com.aspose.words.AutoFitBehavior;
import com.aspose.words.Body;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.SaveFormat;


public class Demo03Paragraph {

    // 输出目录
    static String outputDir = "D://docx//";

    public static void main(String[] args) throws Exception {
        // 输入的Word文件路径
        String inputFilePath = "D://hy.docx";

        Document doc = new Document(inputFilePath);

        // 初始化拆分工具
        DocumentPartSaver saver = new DocumentPartSaver();

        int i = 0;
        // 遍历文档中的段落
        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true)) {
            if (isTitle(paragraph)) {
                if (i++ != 0) {
                    saver.saveCurrentPart();
                }
                saver.startNewPart();
            }

            if (paragraph.getParentNode().getNodeType() == NodeType.TABLE) {
                // 如果段落属于表格，则直接跳过，避免重复处理
                continue;
            }

            saver.addParagraph(paragraph);
        }

        // 保存最后一个部分
        saver.saveCurrentPart();
    }

    /**
     * 检测段落是否为标题
     */
    private static boolean isTitle(Paragraph paragraph) {
        // 判断段落样式是否为标题样式（例如 Heading 1、Heading 2 等）
        String type = paragraph.getParagraphFormat().getStyle().getName();
        return type != null && type.toLowerCase().startsWith("heading 1");
    }

    /**
     * 用于保存文档部分的工具类
     */
    private static class DocumentPartSaver {
        private Document currentDoc;
        private DocumentBuilder builder;
        private int partNumber = 1;
        private Set<Node> importedTables = new HashSet<>();


        public DocumentPartSaver() throws Exception {
            startNewPart();
        }

        /**
         * 开始新部分
         */
        public void startNewPart() throws Exception {
            currentDoc = new Document();
            builder = new DocumentBuilder(currentDoc);

            // 移除默认的空白段落
            if (currentDoc.getFirstSection().getBody().getParagraphs().getCount() > 0) {
                currentDoc.getFirstSection().getBody().getParagraphs().removeAt(0);
            }
        }

        /**
         * 添加段落到当前部分
         */
        public void addParagraph(Paragraph paragraph) throws Exception {
            // 处理表格
            if (paragraph.getParentNode().getNodeType() == NodeType.CELL) {
                // 如果段落属于表格，则导入整个表格
                Node table = paragraph.getAncestor(NodeType.TABLE);
                if (!isTableAlreadyImported(table)) {
                    Node importedTable = currentDoc.importNode(table, true);
                    Body body = currentDoc.getFirstSection().getBody();
                    body.appendChild(importedTable);

                    // 设置表格样式
                    com.aspose.words.Table asposeTable = (com.aspose.words.Table) importedTable;
                    asposeTable.autoFit(AutoFitBehavior.AUTO_FIT_TO_CONTENTS);

                    // 记录已导入的表格
                    importedTables.add(table);
                }
            } else {
                // 否则只导入段落
                Node importedNode = currentDoc.importNode(paragraph, true);
                // 将段落插入到目标文档的主体部分
                Body body = currentDoc.getFirstSection().getBody();
                body.appendChild(importedNode);
            }
        }

        /**
         * 检查目标文档中是否已包含指定表格
         */
        private boolean isTableAlreadyImported(Node table) {
            return importedTables.contains(table);
        }

        /**
         * 保存当前部分
         */
        public void saveCurrentPart() throws Exception {
            if (currentDoc == null || currentDoc.getChildNodes(NodeType.ANY, true).getCount() == 0) {
                return; // 如果当前部分为空，则不保存
            }

            // 保存为新的 Word 文件
            String outputFilePath = outputDir + "output_part_" + partNumber + ".docx";
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                currentDoc.save(fos, SaveFormat.DOCX);
            }

            partNumber++;
        }
    }

}