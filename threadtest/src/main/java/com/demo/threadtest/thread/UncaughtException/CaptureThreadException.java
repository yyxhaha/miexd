package com.demo.threadtest.thread.UncaughtException;

import java.util.concurrent.TimeUnit;

/**
 * Created by yyx on 2021/1/23.
 */
public class CaptureThreadException {
    public static void main(String[] args) {
        /*
        //全局设置
        Thread.setDefaultUncaughtExceptionHandler((t,e)->{
            System.out.println(t.getName()+"  occur exception");
            e.printStackTrace();
        });*/
         final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {

                }
                //这里会报错，然后由UncaughtExceptionHandler捕获
                System.out.println(1/0);
            }
        };

         thread.setUncaughtExceptionHandler((t,e)->{
             System.out.println(t.getName()+"  occur exception");
             e.printStackTrace();
         });
         thread.start();

    }
}
