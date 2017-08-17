package com.example.thread;

/**
 * 使用退出标志终止线程
 * Created by Kin on 2017/6/27.
 */

public class SafeThreadTest extends Thread {
    //volatile，这个关键字的目的是使isContinue同步，也就是说在同一时刻只能由一个线程来修改isContinue的值
    volatile boolean isContinue = true;
    int count;

    @Override
    public void run() {
        while (isContinue) {
            try {
                sleep(1000);
                System.out.println(count++);
                if (count == 10){
                    isContinue = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] arts) {
        new SafeThreadTest().start();
    }
}
