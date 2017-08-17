package com.example.io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by James on 2016/7/27.
 */
public class DataOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        String file = "F:\\java\\io\\output.txt";
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
        outputStream.writeInt(10);
//        outputStream.writeBoolean(true);
        outputStream.writeLong(10L);
        outputStream.writeDouble(10.1);
        outputStream.writeUTF("中国");
        outputStream.writeChars("中国");
        outputStream.close();
        FileInputStreamDemo.printHex(file);
    }
}
