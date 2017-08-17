package com.example.xml;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kin on 2017/7/10.
 */

public class DOM4JTest {
    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new File("resource/books"));
            Element rootElement = document.getRootElement();
            System.out.println(rootElement.asXML());
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                List<Attribute> attributes = element.attributes();
                for (Attribute attribute : attributes) {
                    System.out.print("属性名：" + attribute.getName());
                    System.out.println(",属性值：" + attribute.getText());
                }
//                element.addElement("出版社");
//                element.element("出版社").setText("华中工业出版社");1
                Iterator childIterator = element.elementIterator();
                while (childIterator.hasNext()) {
                    Element child = (Element) childIterator.next();
                    System.out.print("子节点名：" + child.getName());
                    System.out.println(",子节点值：" + child.getText());
                }
            }
            writerToXml(document);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入XML文件
    private static void writerToXml(Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter("resource/books3"), format);
        writer.write(document);
        writer.close();
    }


}
