package com.example.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by James on 2016/7/25.
 */
public class FileUtils {
    /**
     * 列出指定目录下（包括子目录）的所有文件
     *
     * @param dir
     * @throws IOException
     */
    public static void listDirectory(File dir) throws IOException {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录：" + dir + "不存在");
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + "不是目录");
        }

        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listDirectory(file);
                } else {
                    System.out.println(file);
                }
            }
        }

    }
}
