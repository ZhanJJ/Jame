package com.example.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Kin on 2017/7/10.
 */

public class DOMTest {
    public static void main(String[] args) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse("resource/books");
            NodeList nodeList = document.getElementsByTagName("book");
            System.out.println("一共有" + nodeList.getLength() + "本书");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node book = nodeList.item(i);
                NamedNodeMap attributes = book.getAttributes();
                System.out.println("第" + (i + 1) + "本书有" + attributes.getLength() + "个属性");
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attr = attributes.item(j);
                    System.out.print("属性名:" + attr.getNodeName());
                    System.out.println("--属性值:" + attr.getNodeValue());
                }
                NodeList childNodes = book.getChildNodes();
                System.out.println("第" + (i + 1) + "本书有" + childNodes.getLength() + "个子节点");
                for (int k = 0; k < childNodes.getLength(); k++) {
                    Node child = childNodes.item(k);
                    //区分出text类型的node以及element类型的node
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.print("第" + (k + 1) + "个节点的属性名:" + child.getNodeName());
                        System.out.println(",属性值:" + child.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
