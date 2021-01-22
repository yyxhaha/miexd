package com.demo.threadtest.thread.Interrupt;

import java.util.concurrent.TimeUnit;

public class Chapter1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Thread  interrupted");
            }
        });
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println(thread.isInterrupted());
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }
}
