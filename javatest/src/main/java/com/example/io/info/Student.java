package com.example.io.info;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by James on 2016/7/28.
 * transient修饰符，可自行指定序列化的方法，以达到节省资源高效运行的目的
 */
public class Student implements Serializable {
    private String name;
    private String grade;
    private transient int age; //transient修饰过的元素不会进行jvm默认的序列化，但可以自己完成这个元素的序列化

    public Student(int age, String name, String grade) {
        this.age = age;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", age=" + age +
                '}';
    }

    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        s.defaultWriteObject(); //把jvm能默认序列化的元素进行序列化操作
        s.writeInt(age); //将age元素进行序列化
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException{
        s.defaultReadObject(); //把jvm能默认反序列化的元素进行反序列化操作
        this.age = s.readInt(); //将age元素进行反序列化
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
