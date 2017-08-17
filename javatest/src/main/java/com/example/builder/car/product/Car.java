package com.example.builder.car.product;

/**
 * Created by Kin on 2017/3/28.
 * Product：表示被构造的复杂对象
 * ConcreteBuilder创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，以及将这些部件装配成最终产品的接口
 */

public class Car {
    private String engine;  //引擎
    private String tyre;    //轮胎

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTyre() {
        return tyre;
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }
}
