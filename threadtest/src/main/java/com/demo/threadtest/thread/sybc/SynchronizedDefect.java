package com.demo.threadtest.thread.sybc;

import java.util.concurrent.TimeUnit;

/**
 * Created by yyx on 2021/1/23.
 */
public class SynchronizedDefect {
    public synchronized void syncMethod(){
        try{
            TimeUnit.HOURS.sleep(1);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
/**
 * 被synchronized同步的线程不可被中断
 * */
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect synchronizedDefect=new SynchronizedDefect();
        Thread t1=new Thread(synchronizedDefect::syncMethod,"T1");
        t1.start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2=new Thread(synchronizedDefect::syncMethod,"T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(2);
        t2.interrupt();
        System.out.println(t2.isInterrupted());
        System.out.println(t2.getState());
    }
}
