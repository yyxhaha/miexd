package com.topcheer.queryinfo.service.ThreadUtils.queue;



import com.topcheer.queryinfo.service.ThreadUtils.bean.OrderBean;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderBeanQueue {

    private static ConcurrentLinkedQueue<OrderBean> queue = new ConcurrentLinkedQueue<>();


    private static volatile boolean running = false;


    public static void addOrder(OrderBean orderBean) {
        queue.add(orderBean);
    }

    public static synchronized OrderBean getOrder() {
        return queue.poll();
    }

    public static int size() {
        return queue.size();
    }

    public static void clear() {
        queue.clear();
    }


    public static boolean isEmpty() {
        return queue.isEmpty();
    }


    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        OrderBeanQueue.running = running;
    }
}
