package com.example.thread;

/**
 * Created by James on 2016/9/1.
 */
public class ThreadTest extends Thread {
    private static int count = 10;
    private static int count2 = 10;

    static Thread threadB;

    @Override
    public void run() {
        while (count != 0) {
            try {
                System.out.print(count-- + " ");
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ThreadTest().start();
//        joinTest();
    }

    public static void joinTest() {
        Thread threadA = new Thread() {
            @Override
            public void run() {
//                super.run();
                while (count != 0) {
                    System.out.print(count-- + " ");
                    try {
//                        threadB.join();
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        threadA.start();

        threadB = new Thread() {
            @Override
            public void run() {
//                super.run();
                while (count2 != 0) {
                    System.out.print("你大爷 ");
                    count2--;
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        threadB.start();
    }
}
