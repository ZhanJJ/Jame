package com.example.builder;

/**
 * Created by James on 2016/12/14.
 * 建造者模式
 * 构造方法是私有的。也就是说调用者不能直接创建User对象。
 * 属性都是不可变的。所有的属性都添加了final修饰符，并且在构造方法中设置了值。并且，对外只提供getters方法。
 */

public class Student {
    private String name;
    private String profession;
    private int age;

    private Student(Builder builder){
        this.name=builder.name;
        this.profession=builder.profession;
        this.age=builder.age;
    }

    public String toString(){
        return "name="+name+" profession="+profession+" age="+age;
    }

    /**
     * 使用了链式调用。可读性更佳
     * 内部类构造方法中只接收必传的参数,并且该必传的参数使用了final修饰符。
     */
    public static class Builder{
        public final String name; //必传的参数,用了final修饰符
        public String profession;
        public int age;

        public Builder(String name) {
            this.name = name;
        }

        public Builder profession(String profession){
            this.profession = profession;
            return this;
        }

        public Builder age(int age){
            this.age=age;
            return this;
        }

        public Student build(){
            return new Student(this);
        }
    }
}
