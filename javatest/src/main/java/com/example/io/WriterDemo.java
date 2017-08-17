package com.example.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by James on 2016/7/26.
 */
public class WriterDemo {
    public static void main(String[] args) {
        File inputFile;
        File outputFile;
        BufferedReader bufferedReader = null;
        PrintWriter printWrite = null;
        inputFile = new File("F:\\java\\io\\联想.txt");
//        outputFile = new File("F:\\java\\io\\联想2txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
            try {
                printWrite = new PrintWriter(new OutputStreamWriter(new FileOutputStream("F:\\java\\io\\联想2txt"), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                String str = bufferedReader.readLine();
                while (str != null) {
                    printWrite.println(str);
                    str = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWrite != null) {
                    printWrite.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
