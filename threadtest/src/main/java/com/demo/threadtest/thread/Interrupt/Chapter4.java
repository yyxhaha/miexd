package com.demo.threadtest.thread.Interrupt;

import java.util.concurrent.TimeUnit;

public class Chapter4 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("isInterrupted1 :"+isInterrupted());
                    /*
                    * interrupted 方法会擦除重点标记，一次中断只有一个true。
                    * */
                    System.out.println(interrupted());
                    System.out.println("isInterrupted2 :"+isInterrupted());
                }

            }
        };
        thread.setDaemon(true);
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        thread.interrupt();
        /*TimeUnit.MILLISECONDS.sleep(2);
        System.out.println("2"+thread.isInterrupted());*/
    }
}
