package com.demo.threadtest.thread.WaitAndNotify;

import java.util.LinkedList;

public class EventQueue {
    private final int max;
    static class Event{};
    private final LinkedList<Event> eventQueue=new LinkedList<>();
    private final static int DefaultMaxEvent=10;
    public EventQueue(){
        this(DefaultMaxEvent);
    }
    public EventQueue(int maxSize){
        this.max=maxSize;
    }
    public void offer(Event event){
        synchronized (eventQueue){
            if (eventQueue.size()>=max){
                try {

                }
            }
        }
    }
}
