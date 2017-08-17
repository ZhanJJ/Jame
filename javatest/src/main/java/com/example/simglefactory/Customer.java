package com.example.simglefactory;

import com.example.simglefactory.product.BMW3;
import com.example.simglefactory.product.BMW5;

/**
 * Created by James on 2016/5/25.
 */
public class Customer {
    public static void main(String arg[]){
        FactoryBMW factoryBMW = new FactoryBMW();
        BMW3 bmw3 = (BMW3) factoryBMW.getBMW(3);
        BMW5 bmw5 = (BMW5) factoryBMW.getBMW(5);
    }
}
