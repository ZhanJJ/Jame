package com.example.builder;

/**
 * Created by James on 2017/1/10.
 */

public class Apple {
    private String color;
    private double price;

    private Apple(Builder builder) {
        this.color = builder.color;
        this.price = builder.price;
    }

    public String toString() {
        return "color=" + color + "&price=" + price;
    }

    public static class Builder {
        public final String color;
        public double price;

        public Builder(String color) {
            this.color = color;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Apple build() {
            return new Apple(this);
        }
    }

}
