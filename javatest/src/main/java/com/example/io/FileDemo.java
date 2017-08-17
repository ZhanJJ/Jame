package com.example.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by James on 2016/7/25.
 */
public class FileDemo {
    public static void main(String[] asg) throws IOException {
        File mFile = new File("F:\\java\\io");
//        if (!mFile.exists()) {
//            mFile.mkdir();
//        } else {
//            mFile.delete();
//        }
        mFile = new File("F:\\java\\io", "file1.txt");
        if (!mFile.exists()) {
            mFile.createNewFile();
        } else {
            mFile.delete();
        }
        System.out.println(mFile.isDirectory());
        System.out.println(mFile.isFile());
    }


}
