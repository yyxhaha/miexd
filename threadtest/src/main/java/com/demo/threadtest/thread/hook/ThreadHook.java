package com.demo.threadtest.thread.hook;

import java.util.concurrent.TimeUnit;

/**
 * Created by yyx on 2021/1/23.
 */
public class ThreadHook {
    public static void main(String[] args) {
        //为应用注入钩子线程
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("the hook thread 1 is running");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the hook thread 1 will exit");
            }
        });

        //可以注册多个
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("the hook thread 2 is running");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the hook thread 2 will exit");
            }
        });
    }
}
