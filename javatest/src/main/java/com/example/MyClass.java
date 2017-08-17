package com.example;

import com.example.builder.Apple;
import com.example.builder.Student;

public class MyClass {
    public static void main(String[] args) {
        Student student = new Student.Builder("GaLi")
                .age(16)
                .profession("ChuiShui")
                .build();
        System.out.println(student.toString());

        Apple apple = new Apple.Builder("Red")
                .build();

        System.out.println(apple.toString());

//        double d = Math.floor(Double.parseDouble("30000.0"));
        long i = (long) Math.floor(Double.parseDouble("-1000.0"));

        System.out.println(i);

        if (i == 0) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }


}
