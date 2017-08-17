package com.example.thread;

/**
 * Created by James on 2016/9/1.
 */
public class RunnableTest extends Object implements Runnable {
    int count = 10;

    @Override
    public void run() {
        while (count != 0) {
            System.out.print(count-- + " ");
        }
    }

    public static void main(String[] arts) {
        new Thread(new RunnableTest()).start();
    }

}
