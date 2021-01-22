package com.demo.threadtest.thread.join;





public class MyThread extends Thread {
    public MyThread(String a) {
        setName(a);
    }

    @Override
    public void run() {
        while (!isInterrupted())
        System.out.println(Thread.currentThread().getName());
    }
}
