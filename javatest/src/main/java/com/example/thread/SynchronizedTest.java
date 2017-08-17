package com.example.thread;

/**
 * Created by James on 2016/9/3.
 */
public class SynchronizedTest {
    static int num = 10;

    static class CountThread extends Thread {
        @Override
        public void run() {
            synchronized (""){
                while (num > 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("tickets" + num--);
                }
            }
        }
    }

    public static void main(String[] args) {
        new CountThread().start();
        new CountThread().start();
        new CountThread().start();
        new CountThread().start();
    }


}
