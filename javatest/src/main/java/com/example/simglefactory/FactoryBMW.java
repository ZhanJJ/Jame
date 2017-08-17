package com.example.simglefactory;

import com.example.simglefactory.product.BMW;
import com.example.simglefactory.product.BMW3;
import com.example.simglefactory.product.BMW5;

/**
 * Created by James on 2016/5/25.
 */
public class FactoryBMW {
    public  BMW getBMW(int type) {
        switch (type) {
            case 3: {
                return new BMW3();
            }
            case 5: {
                return new BMW5();
            }
            default:
                return null;
        }
    }
}
