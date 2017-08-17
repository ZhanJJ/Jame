package com.example.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by James on 2016/7/26.
 */
public class ReaderDemo {
    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
        try {
            while (true) { //while(true){}循环保证程序不会结束
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String str = bufferedReader.readLine();
                if (str != null && str.length() != 0) {
                    System.out.println(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
