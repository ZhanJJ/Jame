package com.example.builder.car.concreteBuilder;

import com.example.builder.car.builder.Builder;
import com.example.builder.car.product.Car;

/**
 * Created by Kin on 2017/3/28.
 * ConcreteBuilder:实现Builder的接口,以构造和装配该产品的各个部件
 * （定义并明确它所创建的表示，并提供一个检索产品的接口)
 */

public class AudiBuilder implements Builder {
    Car audiCar;

    public AudiBuilder(){
        audiCar = new Car();
    }

    @Override
    public void builderEngine() {
        audiCar.setEngine("奥迪引擎");
    }

    @Override
    public void builderTyre() {
        audiCar.setTyre("奥迪轮胎");
    }

    @Override
    public Car builderCar() {
        return audiCar;
    }
}
