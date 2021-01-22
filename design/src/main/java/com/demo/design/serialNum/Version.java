package com.demo.design.serialNum;


import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Version extends Observable {

    private String btype;

    private long maxVal;

    private int step;

    private AtomicLong currVal;

    private ReentrantReadWriteLock lock = null;

    /**
     * 构造方法
     */
    public Version(String btype, Observer o) {
        this.btype = btype;
        this.maxVal = 0l;
        this.currVal = new AtomicLong(0);
        this.lock = new ReentrantReadWriteLock();
        this.addObserver(o);
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    /**
     * 获取版本
     *
     * @return 版本号
     */
    public String getVersion(String path) {
        String version = "";
        try {
            // 共享读锁
            lock.readLock().lock();
            if (checkVal()) {
                version = String.valueOf(currVal.getAndAdd(1));
            } else {
                lock.readLock().unlock();
                // 排它写锁
                lock.writeLock().lock();
                try {
                    if (checkVal()) {
                        getVersionFromDB(path);
                    }
                    version = String.valueOf(currVal.getAndAdd(1));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }
                lock.readLock().lock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        if (!checkVal()) {
            notifyObservers(path);
        }
        return version;
    }

    /**
     * 检查版本号是否可用
     *
     * @return 成功或者失败
     */
    private boolean checkVal() {
        return maxVal > currVal.get();
    }

    /**
     * 从数据库中取出可用版本号
     */
    public void getVersionFromDB(String path) {

        Long dbVersion = DBUtil.getVersionFromDB(this.btype, path);
        // 设置当前值
        currVal.set(dbVersion);
        step = 10;
        maxVal = dbVersion + step;
        DBUtil.updateVersionFromDB(this.btype, maxVal, path);

    }
}

