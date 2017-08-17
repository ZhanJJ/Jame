package com.example.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by James on 2016/7/27.
 * InputStreamReader
 * OutputStreamWriter
 *
 */
public class IsrAndOswDemo {
    public static void main(String[] args) throws IOException{
        InputStreamReader isr = new InputStreamReader(new FileInputStream("F:\\java\\io\\one.txt"),"utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("F:\\java\\io\\one2.txt"),"utf-8");
//        int i;
//        while ((i=isr.read())!=-1){
//            System.out.print((char) i);
//        }

        char[] chars = new char[8*1024];
        int length;
        while ((length=isr.read(chars,0,chars.length))!=-1){
            String str = new String(chars,0,length);
            osw.write(str);
            System.out.print(str);
        }

        isr.close();
        osw.close();
    }


}
