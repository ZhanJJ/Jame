package com.example.io;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/26.
 */
public class FileInputStreamDemo {
    public static void main(String[] args) throws IOException {
        printHex("F:\\java\\io\\io流.png");
//        printHexByteArray("F:\\java\\io\io流.png");
    }

    /**
     * 读取指定文件内容，并以16进制打印出来
     * 一个字节一个字节读取
     *
     * @param fileName
     * @throws IOException 单字节读取
     */
    public static void printHex(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        int b;
        int i = 1;
        while ((b = fileInputStream.read()) != -1) {
            if (b < 0xF) {
                System.out.print("0");
            }
            System.out.print(Integer.toHexString(b) + " ");
            if (i++ % 10 == 0) {
                System.out.println();
            }
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
    }

    /**
     * 读取指定文件内容，并以16进制打印出来
     * 连续字节读取
     *
     * @param fileName
     * @throws IOException
     */
    public static void printHexByteArray(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte[] bytes = new byte[10 * 1024];
        int length = 0;
        int j = 0;
        while ((length = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
            for (int i = 0; i < length; i++) {
                System.out.print(Integer.toHexString(bytes[i] & 0xff) + " ");
                if (j++ % 10 == 0) {
                    System.out.println();
                }
            }
        }
        fileInputStream.close();
    }

}
