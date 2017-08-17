package com.example.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/27.
 */
public class BufferedInputStreamDemo {
    public static void main(String[] args) throws IOException {
        Long start = System.currentTimeMillis();
//        IoUtils.copyFile(new File("F:\\java\\io\\music.mp3"), new File("F:\\java\\io\\musicCopy3.mp3")); //花费时间2
        copyFileByBuffer(new File("F:\\java\\io\\music.mp3"), new File("F:\\java\\io\\musicCopy2.mp3")); //花费时间:flush8307, 无flush121
//        copyFileByByte(new File("F:\\java\\io\\music.mp3"), new File("F:\\java\\io\\musicCopy.mp3")); //花费时间13055
        Long end = System.currentTimeMillis();
        System.out.print("花费时间" + (end - start));
    }

    /**
     * 将一个文件拷贝到另一个文件
     * 使用buffer缓冲
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFileByBuffer(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件：" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不是文件");
        }
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
            int i;
            while ((i = inputStream.read()) != -1) {
                outputStream.write(i);
            }
            outputStream.flush();//flush,防止在close之前有缓冲没有完全读完就被强行关闭了文件
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将一个文件拷贝到另一个文件
     * 一个个字节拷贝
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFileByByte(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件：" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不是文件");
        }
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(destFile);
            int i;
            while ((i = inputStream.read()) != -1) {
                outputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
