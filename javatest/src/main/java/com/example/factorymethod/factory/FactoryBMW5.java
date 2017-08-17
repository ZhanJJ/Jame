package com.example.factorymethod.factory;

import com.example.factorymethod.product.BMW;
import com.example.factorymethod.product.BMW5;

/**
 * Created by James on 2016/5/25.
 */
public class FactoryBMW5 implements FactoryBMW {
    @Override
    public BMW createBMW() {
        return new BMW5();
    }
}
