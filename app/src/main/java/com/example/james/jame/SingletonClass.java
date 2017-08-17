package com.example.james.jame;

/**
 * Created by James on 2016/5/20.
 */
public class SingletonClass {
    //单例模式1，使用volatile关键字
    /**
     * 在JDK 5之后，Java使用了新的内存模型。volatile关键字有了明确的语义 ——
     *在JDK1.5之前，volatile是个关键字，但是并没有明确的规定其用途——被volatile修饰的写变量不能和之前的读写代码调整，读变量不能和之后的读写代码调整
     */
//    private volatile static SingletonClass instance = null;
//
//    public static SingletonClass getInstance() {
//        if (instance == null) {
//            synchronized (SingletonClass.class) {
//                if (instance == null) {
//                    instance = new SingletonClass();
//                }
//            }
//        }
//        return instance;
//    }
//
//    private SingletonClass() {
//
//    }

    //单例模式2，使用静态内部类
    /**
     *我们使用了Java的静态内部类。这一技术是被JVM明确说明了的，因此不存在任何二义性。
     * 在这段代码中，因为SingletonClass没有static的属性，因此并不会被初始化。
     * 直到调用getInstance()的时候，会首先加载SingletonClassInstance类，这个类有一个static的SingletonClass实例，
     * 因此需要调用SingletonClass的构造方法，然后getInstance()将把这个内部类的instance返回给使用者。由于这个instance是static的，因此并不会构造多次。
     *
     * 由于SingletonClassInstance是私有静态内部类，所以不会被其他类知道，同样，static语义也要求不会有多个实例存在。
     * 并且，JSL规范定义，类的构造必须是原子性的，非并发的，因此不需要加同步块。同样，由于这个构造是并发的，所以getInstance()也并不需要加同步。
     */
    private SingletonClass(){}

    private static class SingletonInstanceClass{
        private static SingletonClass instance = new SingletonClass();
    }

    public SingletonClass getInstance(){
        return SingletonInstanceClass.instance;
    }
}
