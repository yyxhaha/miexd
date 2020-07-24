package com.topcheer.queryinfo.service.ThreadUtils;




import com.topcheer.queryinfo.service.ThreadUtils.bean.OrderBean;
import com.topcheer.queryinfo.service.ThreadUtils.queue.OrderBeanQueue;
import com.topcheer.queryinfo.service.ThreadUtils.thread.OrderBeanThread;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class ThreadService {


    public static volatile int threadStatus = 0;
    private final int maxThreadPoolSize=20;
    private final ExecutorService executorService = Executors.newFixedThreadPool(maxThreadPoolSize);

    public synchronized void execute(List<OrderBean> orders, int threadSize) throws Exception {
        if(orders.size()==0){
            return;
        }
        Iterator<OrderBean> iterator=orders.iterator();
        while (iterator.hasNext()){
            OrderBeanQueue.addOrder(iterator.next());
        }
        int size=Math.min(threadSize,maxThreadPoolSize);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        for (int i=0;i<size;i++) {
            OrderBeanThread thread = new OrderBeanThread("thread"+i,countDownLatch);
            executorService.submit(thread);
        }
        countDownLatch.await();
        if (threadStatus > 0) {
            throw new Exception("线程异常，程序异常退出！");
        }
    }


}
