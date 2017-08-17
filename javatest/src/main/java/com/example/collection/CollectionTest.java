package com.example.collection;

import java.util.LinkedList;

/**
 * Created by James on 2016/12/21.
 */

public class CollectionTest {
    public static void main(String[] args){
        LinkedList<String> mLinkedList = new LinkedList<>();
        mLinkedList.add("1");
        mLinkedList.add("2");
        mLinkedList.add("3");
        mLinkedList.offer("4");


        for (String s : mLinkedList) {
            System.out.println(s);
        }
//        String strTemp = mLinkedList.peek();

    }
}
