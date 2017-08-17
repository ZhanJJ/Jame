package com.example.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kin on 2017/6/27.
 */

public class SafeThreadTest2 extends Thread {
    private long lastSecond;

    @Override
    public void run() {
        //线程未进入阻塞状态，使用isInterrupted()判断线程的中断标志来退出循环
        // 当使用interrupt()方法时，中断标志就会置true，和使用自定义的标志来控制循环是一样的道理。
        while (!isInterrupted()) {
            //do something, but no throw InterruptedException
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
            long currentSecond = Long.parseLong(simpleDateFormat.format(date));
            if (currentSecond == 0 || currentSecond > lastSecond) { //间隔一秒操作一次
                lastSecond = currentSecond;
                System.out.println(lastSecond);
            }
        }
    }

    public static void main(String[] args) {
        SafeThreadTest2 thread2 = new SafeThreadTest2();
        thread2.start();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thread2.interrupt(); //当使用interrupt()方法时，isInterrupted（）返回中断标志就会置true
            }
        }, 5 * 1000); //延迟5秒执行
    }
}
