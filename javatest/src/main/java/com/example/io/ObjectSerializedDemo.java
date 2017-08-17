package com.example.io;

import com.example.io.info.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by James on 2016/7/28.
 * 对象的序列化（指将对象转换为字节序列的过程）、反序列化
 * 对象序列化：将object转换成byte序列，反之叫对象的反序列化
 * <p/>
 * 序列化接口（Serializeble）
 * 对象必须实现这个接口，只是一个标准，没有任何方法
 */
public class ObjectSerializedDemo {
    public static void main(String[] args) throws IOException {
        String file = "demo/student.dat";

//        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
//        Student student = new Student(21, "旮旯", "202");
//        outputStream.writeObject(student);
//        outputStream.close();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        try {
            Student student = (Student) inputStream.readObject();
            System.out.print(student.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }

    }
}

