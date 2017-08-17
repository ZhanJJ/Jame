package com.example.builder.car;

import com.example.builder.car.concreteBuilder.AudiBuilder;
import com.example.builder.car.concreteBuilder.BenzBuilder;
import com.example.builder.car.director.Director;
import com.example.builder.car.product.Car;

/**
 * Created by Kin on 2017/3/28.
 */

public class CarBuilderTest {
    public static void main(String[] arts) {
        Director director = new Director();
        Car audiCar = director.constructCar(new AudiBuilder());
        Car benzCar = director.constructCar(new BenzBuilder());
    }
}
