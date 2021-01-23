### interrupt
1. isInterrupted:线程是否被中断
2. interrupted：线程是否被中断。如果被中断，只有第一次输出为true，
   之后会清楚中断标记返回false，除非该线程再次被中断
3. 可中断方法（sleep、wait、join、InterruptibleChannel的io操作等等）会捕捉中断信号，
###wait notify 学习笔记
1. 这两个方法都属于object
2. 必须是持有monitor对象才可及调用,如：
   ````java
   class Test{
       public void test(){
           synchronized (eventQueue){
                eventQueue.wait();
                eventQueue.notify();
           }
       }
   }
   ````
   这两个方法允许的前提是必须持有同步方法的所有权
3. wait方法是可中断方法。线程执行wait方法后，会加入对于的wait set中，
   每个monitor都有一个与之关联的wait set，notify可以将其唤醒，
   也就是从wait set中弹出
4. notify是唤醒一个线程，notifyAll是唤醒所有线程（同一个wait set）。
   notify是先进先出还是先进后出看jdk，有的相同厂家不同jdk版本也不一样，jvm规范没有要求。
5. wait和sleep都会使线程进入阻塞状态，都是可中断方法。 
6. sleep是线程特有的方法，wait是Object的方法。
7. 线程在同步方法执行sleep不会释放monitor锁，wait方法会。
8. 调用wait之后，会是释放monitor的所有权，然后进入阻塞状态。被中断之后需要重新获取monitor的所有权
9. 被synchronized同步的线程不可被中断

###hook
在jvm中没有活跃的非守护线程的守护，即jvm退出之前，调用hook线程。
可以用于关闭句柄、删除守护文件等操作。