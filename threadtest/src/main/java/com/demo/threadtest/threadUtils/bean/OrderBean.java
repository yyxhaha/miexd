package com.topcheer.queryinfo.service.ThreadUtils.bean;


public abstract class OrderBean {
    private String id;
    private String name;
    private String status;
    private Object param;

    public abstract void execute();

    public OrderBean() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
