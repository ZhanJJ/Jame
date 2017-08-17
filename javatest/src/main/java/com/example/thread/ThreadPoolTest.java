package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Kin on 2017/5/14.
 */

public class ThreadPoolTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(runnable);

        ExecutorService cachedThreadPool= Executors.newCachedThreadPool();
        cachedThreadPool.execute(runnable);
    }
}