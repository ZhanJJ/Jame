package com.example.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Java泛型使用示例整理
 * Created by James on 2017/1/10.
 */

public class GenericTest {
    /**
     * 类定义时，使用泛型
     * 类名后面增加<T>，说明是泛型类。T可以视为类型的占位符。泛型类的代码就可以使用这个占位符T。
     *
     * @param <T>
     */
    static class Demo<T> {
        T field;

        public T getField() {
            return field;
        }

        public void setField(T field) {
            this.field = field;
        }

        //无参泛型方法
        //在方法的返回值前面，修饰符后面增加<T>，表示为泛型方法，使得在代码中用T代表类型
        public <T> List<T> newArrayList() {
            return new ArrayList<T>();
        }

        //有参泛型方法
        //同无参泛型方法，类型的确定，是根据参数类型自动推导
        public <T> void getType(T t) {
            System.out.println(t.getClass().getName());
            return;
        }

        //使用通配符<T extends Number>，表示传入的类型必须是Number或者其子类
        public <T extends Number> void getType(T t) {
            System.out.println(t.getClass().getName());
            return;
        }

        //通配符 ?  表示该元素类型未知，可以是任何类型

        //边界通配符总结
        //如果你想从一个数据类型里获取数据，使用 ? extends 通配符
        //如果你想把对象写入一个数据结构里，使用 ? super 通配符
        //如果你既想存，又想取，那就别用通配符。
    }

    public static void main(String[] args) {
        Demo<String> demo = new Demo<String>();
        //泛型方法，类型的确定，根据等号左边的类型来推导泛型的最终类型
        List<String> strList = demo.newArrayList(); //无参
        strList.add("这是测试1");
        demo.getType(2); //有参
    }

}
