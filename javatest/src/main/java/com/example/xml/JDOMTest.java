package com.example.xml;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kin on 2017/7/10.
 */

public class JDOMTest {
    private static List<Book> booksList = new ArrayList<>();

    public static void main(String[] args) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            InputStream in = new FileInputStream("resource/books");
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            // 3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
            Document document = saxBuilder.build(isr);
            // 4.通过document对象获取xml文件的根节点
            Element element = document.getRootElement();
            // 5.获取根节点下的子节点的List集合
            List<Element> bookList = element.getChildren();
            for (int i = 0; i < bookList.size(); i++) {
                Book bookEntity = new Book();
                System.out.println("开始解析第" + (i + 1) + "本书");
                // 解析book的属性集合
                List<Attribute> attris = bookList.get(i).getAttributes();
                System.out.print((i+1)+" name:"+ bookList.get(i).getName());
                System.out.println((i+1)+" value:"+ bookList.get(i).getValue());
                for (Attribute attr : attris) {
                    String attrName = attr.getName();
                    String attrValue = attr.getValue();
                    if (attrName.equals("id")) {
                        bookEntity.id = attrValue;
                    }
                }
                // 对book节点的子节点的节点名以及节点值的遍历
                List<Element> bookChilds = bookList.get(i).getChildren();
                for (int j = 0; j < bookChilds.size(); j++) {
                    Element child = bookChilds.get(j);
                    if (child.getName().equals("name")) {
                        bookEntity.name = child.getValue();
                    } else if (child.getName().equals("author")) {
                        bookEntity.author = child.getValue();
                    } else if (child.getName().equals("year")) {
                        bookEntity.year = child.getValue();
                    } else if (child.getName().equals("price")) {
                        bookEntity.price = child.getValue();
                    } else if (child.getName().equals("language")) {
                        bookEntity.language = child.getValue();
                    }
                }
                booksList.add(bookEntity);
                bookEntity = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
