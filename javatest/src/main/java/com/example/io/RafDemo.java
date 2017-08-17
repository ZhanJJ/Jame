package com.example.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by James on 2016/7/26.
 */
public class RafDemo {
    public static void main(String[] args) throws IOException{
        RandomAccessFile raf = null;
        File directory = new File("Demo");
        if (!directory.exists()){
            directory.mkdir();
        }
        File file = new File(directory,"raf.dat");
        if (!file.exists()){
            file.createNewFile();
        }

        raf = new RandomAccessFile(file,"rw");
        System.out.println(raf.getFilePointer());

        raf.write('A'); //write每次只能写一个字节
        raf.write('A'); //write每次只能写一个字节
        System.out.println(raf.getFilePointer());


        int i = 0x7fffffff;
        raf.writeInt(i);
        System.out.println(raf.getFilePointer());

        String str = "中";
        byte[] bytes = str.getBytes("gbk");
        raf.write(bytes);
        System.out.println(raf.getFilePointer());

        raf.seek(0);
        byte[] buf = new byte[(int) raf.length()];
        raf.read(buf);
        System.out.println(buf);

        for (byte b:buf){
            System.out.print(Integer.toHexString(b & 0xff)+" ");
        }

        if (raf!=null){
            raf.close();
        }


    }
}
