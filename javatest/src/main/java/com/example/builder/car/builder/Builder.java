package com.example.builder.car.builder;

import com.example.builder.car.product.Car;

/**
 * Created by Kin on 2017/3/28.
 * Builder：为创建一个产品对象的各个部件指定抽象接口。
 */

public interface Builder {
    void builderEngine();

    void builderTyre();

    Car builderCar();
}
