package com.topcheer.queryinfo.service.ThreadUtils.thread;


import com.topcheer.queryinfo.service.ThreadUtils.common.MemoryCache;

import com.topcheer.queryinfo.service.ThreadUtils.bean.OrderBean;
import com.topcheer.queryinfo.service.ThreadUtils.queue.OrderBeanQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;


public class OrderBeanThread implements Runnable {

    private static Logger logger = LogManager.getLogger(OrderBeanThread.class);

    private String threadName;

    private CountDownLatch countDownLatch;

    public OrderBeanThread(String threadName, CountDownLatch countDownLatch) {
        this.threadName = threadName;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            while (!OrderBeanQueue.isEmpty()) {
                OrderBean orderBean = OrderBeanQueue.getOrder();
                if (orderBean == null) {
                    continue;
                }

                try {
                    //Thread.sleep(1000);
                    orderBean.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(e);
                }
                //logger.info("thread"+threadName+" |countDownLatch:"+countDownLatch.getCount());
                //System.out.println(threadName+" |countDownLatch:"+countDownLatch.getCount());
                logger.info(threadName + "——" + orderBean.getId());
                MemoryCache.orderBeanMap.put(orderBean.getId(), orderBean);
            }
        } catch (Exception e) {
            throw new RuntimeException("线程处理异常，线程退出");

        } finally {
            countDownLatch.countDown();
            logger.info(countDownLatch.getCount());
        }
    }
}
