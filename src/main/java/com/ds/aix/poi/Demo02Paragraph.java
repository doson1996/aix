package com.ds.aix.poi;

import com.aspose.words.Body;
import com.aspose.words.Document;
import com.aspose.words.LayoutCollector;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.Section;

/**
 * @author ds
 * @date 2025/3/13
 * @description 按照段落拆分
 */
public class Demo02Paragraph {

    public static void main(String[] args) throws Exception {
        // 输入的Word文件路径
        String inputFilePath = "D://hy.docx";
        // 输出目录
        String outputDir = "D://docx//";
        Document doc = new Document(inputFilePath);
        LayoutCollector layoutCollector = new LayoutCollector(doc);

        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true)) {
            String name = paragraph.getParagraphFormat().getStyle().getName();
            System.out.println("name = " + paragraph.getText().trim() + "type = " + name);
            if ("Heading 1".equalsIgnoreCase(name)) {
                int pageIndex = layoutCollector.getStartPageIndex(paragraph);
                int endIndex = layoutCollector.getEndPageIndex(paragraph);

                Document headingDoc = new Document();
                Section firstSection = headingDoc.getFirstSection();
                Body body = firstSection.getBody();

                for (int i = pageIndex; i <= endIndex; i++) {
                    // 克隆源文档中的 Section
                    Section sourceSection = doc.getSections().get(i);
                    Section clonedSection = (Section) sourceSection.deepClone(true);

                    // 将克隆的 Section 导入到目标文档
                    Section importedSection = (Section) headingDoc.importNode(clonedSection, true);

                    // 将导入的 Section 添加到目标文档中
                    headingDoc.appendChild(importedSection);
                }

// 保存生成的文档
                headingDoc.save(outputDir + "output_heading_" + pageIndex + ".docx");

//                Document headingDoc = new Document();
//                Section firstSection = headingDoc.getFirstSection();
//                Body body = firstSection.getBody();
//
//                for (int i = pageIndex; i <= endIndex; i++) {
//                    // 先克隆节点，然后将其导入到新的文档中
//                    Node clonedSection = doc.getSections().get(i).deepClone(true);
//                    Node importedSection = headingDoc.importNode(clonedSection, true);
//                    body.appendChild(importedSection);
//                }
//
//                headingDoc.save(outputDir + "output_heading_" + paragraph.getText().trim() + ".docx");
            }
        }

//        for (Paragraph paragraph : (Iterable<Paragraph>) doc.getChildNodes(NodeType.PARAGRAPH, true)) {
//            String name = paragraph.getParagraphFormat().getStyle().getName();
//            System.out.println("name = " + name);
//            if ("TOC 1".equalsIgnoreCase(name)) {
//                int pageIndex = layoutCollector.getStartPageIndex(paragraph);
//                int endIndex = layoutCollector.getEndPageIndex(paragraph);
//
//                Document headingDoc = new Document();
//                for (int i = pageIndex; i <= endIndex; i++) {
//                    headingDoc.getFirstSection().getBody().appendChild(doc.getSections().get(i).deepClone(true));
//                }
//
//                headingDoc.save(outputDir + "output_heading_" + paragraph.getText().trim() + ".docx");
//            }
//        }

    }

}
