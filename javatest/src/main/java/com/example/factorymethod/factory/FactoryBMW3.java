package com.example.factorymethod.factory;

import com.example.factorymethod.product.BMW3;

/**
 * Created by James on 2016/5/25.
 */
public class FactoryBMW3 implements FactoryBMW {
    @Override
    public com.example.factorymethod.product.BMW createBMW() {
        return new BMW3();
    }
}
