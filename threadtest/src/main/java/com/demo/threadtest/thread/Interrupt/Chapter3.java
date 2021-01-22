package com.demo.threadtest.thread.Interrupt;

import java.util.concurrent.TimeUnit;

public class Chapter3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        TimeUnit.MINUTES.sleep(1);
                        //TimeUnit.SECONDS.sleep(10);
                    }
                    catch (InterruptedException ex){
                        //interrupt flag clear
                        System.out.println("interrupted:"+isInterrupted());
                    }
                }

            }
        };
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println("1"+thread.isInterrupted());
        thread.interrupt();
        /*
        * sleep 2确保 中断信号 被run方法的sleep捕获，为了不影响线程中其他方法的执行，线程的interrupt标记复位
        * */
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println("2"+thread.isInterrupted());
    }
}
