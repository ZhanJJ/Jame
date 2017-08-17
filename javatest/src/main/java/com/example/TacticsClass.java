package com.example;

/**
 * Created by Kin on 2017/3/21.
 */

public class TacticsClass {
    public static void main(String[] args) {
        ColorChoice colorChoice = new ColorChoice(new White());
        colorChoice.whichColor();
    }

    interface PrintColor {
        void print();
    }

    static class White implements PrintColor {
        @Override
        public void print() {
            System.out.println("White");
        }
    }

    static class Blue implements PrintColor {
        @Override
        public void print() {
            System.out.println("Blue");
        }
    }

    static class Red implements PrintColor {
        @Override
        public void print() {
            System.out.println("Red");
        }
    }

    static class ColorChoice {
        private PrintColor printColor;

        ColorChoice(PrintColor printColor) {
            this.printColor = printColor;
        }

        void whichColor() {
            printColor.print();
        }
    }

}
