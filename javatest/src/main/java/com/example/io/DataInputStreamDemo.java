package com.example.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/27.
 */
public class DataInputStreamDemo {
    public static void main(String[] args) throws IOException {
        DataInputStream inputStream = new DataInputStream(new FileInputStream("F:\\java\\io\\output.txt"));
        int i = inputStream.readInt();
        System.out.println(i);
        long l = inputStream.readLong();
        System.out.println(l);
        Double d = inputStream.readDouble();
        System.out.println(d);
        String str = inputStream.readUTF();
        System.out.println(str);

        inputStream.close();
    }

}

