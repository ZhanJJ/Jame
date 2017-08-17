package com.example.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/26.
 */
public class FileOutputStreamDemo {
    public static void main(String[] args) throws IOException {
//        FileOutputStream outputStream = new FileOutputStream("F:\\java\\io\\output.txt");
//        outputStream.write('A');
//        outputStream.write('B');
//
//        int a = 10;
//        outputStream.write(a>>>24);
//        outputStream.write(a>>>16);
//        outputStream.write(a>>>8);
//        outputStream.write(a);
//
//        byte[] bytes = "中国".getBytes("gbk");
//        outputStream.write(bytes);
//
//        FileInputStreamDemo.printHex("F:\\java\\io\\output.txt");
//        outputStream.close();

        copyFile(new File("F:\\java\\io\\联想.txt"), new File("F:\\java\\io\\联想copy.txt"));
    }

    /**
     * 将一个文件内容拷贝到另一个文件
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

//        FileInputStream inputStream = new FileInputStream(srcFile);
//        FileOutputStream outputStream = new FileOutputStream(destFile);
//        byte[] bytes = new byte[10 * 1024];
//        int length;
//        if ((length = inputStream.read(bytes, 0, bytes.length)) != 1) {
//            outputStream.write(bytes, 0, length);
//        }
//        outputStream.flush();
//        inputStream.close();
//        outputStream.close();

        FileInputStream inputStream = new FileInputStream(srcFile);
        FileOutputStream outputStream = new FileOutputStream(destFile);
        byte[] bytes = new byte[10*1024];
        int length =0;
        while ((length=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,length);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

}
