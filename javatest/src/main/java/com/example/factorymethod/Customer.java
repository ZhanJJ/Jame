package com.example.factorymethod;

import com.example.factorymethod.factory.FactoryBMW3;
import com.example.factorymethod.factory.FactoryBMW5;
import com.example.factorymethod.product.BMW3;
import com.example.factorymethod.product.BMW5;

/**
 * Created by James on 2016/5/25.
 */
public class Customer {
    public static void main(String args[]) {
        FactoryBMW3 factoryBMW3 = new FactoryBMW3();
        BMW3 bmw3 = (BMW3) factoryBMW3.createBMW();

        FactoryBMW5 factoryBMW5 = new FactoryBMW5();
        BMW5 bmw5 = (BMW5) factoryBMW5.createBMW();
    }
}
