package com.demo.threadtest.thread.WaitAndNotify;

import java.util.concurrent.TimeUnit;

/**
 * Created by yyx on 2021/1/23.
 */
public class EventClient {
    public static void main(String[] args) {
        final EventQueue eventQueue=new EventQueue();
        new Thread(() -> {
            for (;;){
                eventQueue.offer(new EventQueue.Event());
            }
        }).start();
        new Thread(() -> {
            for (;;){
                eventQueue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        },"consumer").start();
    }
}
