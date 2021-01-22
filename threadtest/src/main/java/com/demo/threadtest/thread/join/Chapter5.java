package com.demo.threadtest.thread.join;


public class Chapter5 {
    public static void main(String[] args) throws InterruptedException {
        MyThread a=new MyThread("A");
        MyThread b=new MyThread("B");
        /*
        * join 方法会阻塞当前线程，即：调用join的线程会被阻塞
        *  如果按照注释代码的顺序调用，b将会在a执行完毕后执行
        a.start();
        a.join();
        b.start();
        b.join();
        * */
        a.start();
        b.start();
        a.join();
        b.join();

    }
}
