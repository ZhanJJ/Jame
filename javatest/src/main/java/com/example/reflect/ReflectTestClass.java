package com.example.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by James on 2017/1/9.
 */

public class ReflectTestClass {

    public static void main(String[] args) {
        ReflectTestClass a = new ReflectTestClass();

//        try {
//            //动态加载类
//            Class ob = Class.forName("com.example.reflect.ReflectTestClass");
//            System.out.println(ob.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        printClassInfo(a);
        printClassField(a);
    }

    public void toUpper(String a) {
        System.out.println(a.trim().toUpperCase());
    }

    private void add(int a, int b) {
        System.out.println(a + b);
    }

    public static void printClassInfo(Object object){
        Class c = object.getClass();
        System.out.println("类的名称："+c.getName());

       Method[] methods=c.getMethods();
        for (Method method:methods){
            System.out.println("方法名："+method.getName()
                    +" 返回类型："+method.getReturnType().getSimpleName());
            Class[] parameters=method.getParameterTypes();
            for (Class parameter:parameters){
                System.out.println("方法参数："+parameter.getSimpleName());
            }
        }
    }

    //获取成员变量信息
    public static void printClassField(Object object){
        Class c =object.getClass();
        Field[] fields = c.getFields();
        for (Field field:fields){
            System.out.println("成员变量名："+field.getName()+"变量类型"+field.getType().getSimpleName());
        }
    }
}
