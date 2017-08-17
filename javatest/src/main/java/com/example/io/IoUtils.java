package com.example.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/27.
 */
public class IoUtils {


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

    /**
     * 将一个文件内容拷贝到另一个文件
     * 批量读取字节
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件：" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不存在");
        }

        FileInputStream inputStream = new FileInputStream(srcFile);
        FileOutputStream outputStream = new FileOutputStream(destFile);
        byte[] bytes = new byte[10 * 1024];
        int length;
        if ((length = inputStream.read(bytes, 0, bytes.length)) != 1) {
            outputStream.write(bytes, 0, length);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }

}
