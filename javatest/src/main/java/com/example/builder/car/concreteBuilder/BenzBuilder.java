package com.example.builder.car.concreteBuilder;

import com.example.builder.car.builder.Builder;
import com.example.builder.car.product.Car;

/**
 * Created by Kin on 2017/3/28.
 * ConcreteBuilder:实现Builder的接口,以构造和装配该产品的各个部件
 * （定义并明确它所创建的表示，并提供一个检索产品的接口)
 */

public class BenzBuilder implements Builder {
    Car benzCar;

    public BenzBuilder(){
        benzCar = new Car();
    }

    @Override
    public void builderEngine() {
        benzCar.setEngine("奔驰引擎");
    }

    @Override
    public void builderTyre() {
        benzCar.setTyre("奔驰轮胎");
    }

    @Override
    public Car builderCar() {
        return benzCar;
    }
}
