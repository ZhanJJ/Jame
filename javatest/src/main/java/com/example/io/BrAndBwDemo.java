package com.example.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by James on 2016/7/28.
 */
public class BrAndBwDemo {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("F:\\java\\io\\one.txt"));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("F:\\java\\io\\one3.txt"));
        PrintWriter printWriter = new PrintWriter("F:\\java\\io\\one3.txt");
        String str;
        while (((str = bufferedReader.readLine()) != null)) {
            System.out.println(str);
//            bufferedWriter.write(str);
//            bufferedWriter.newLine();
            printWriter.println(str);
        }

        bufferedReader.close();
//        bufferedWriter.close();
        printWriter.flush();
        printWriter.close();
    }
}
