package com.example.io;

/**
 * Created by James on 2016/7/25.
 */
public class EncodeDemo {
    public static void main(String[] args) throws Exception {
        String str1 = "慕课ABC";
        byte[] bytes1 = str1.getBytes("utf-16be"); //转换成字节是项目默认编码

        for (byte b : bytes1) {
//            System.out.print(Integer.toHexString(b)+" ");
        }
        System.out.println(Integer.toHexString(-2));
        System.out.println(Integer.valueOf("fe", 16));
    }
}
