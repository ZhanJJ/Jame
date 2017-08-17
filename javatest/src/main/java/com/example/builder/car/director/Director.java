package com.example.builder.car.director;

import com.example.builder.car.builder.Builder;
import com.example.builder.car.product.Car;

/**
 * Created by Kin on 2017/3/28.
 * Director：构造一个使用Builder接口的对象，指导构建过程。
 */

public class Director {
    public Car constructCar(Builder builder) {
        builder.builderEngine();
        builder.builderTyre();
        return builder.builderCar();
    }
}
