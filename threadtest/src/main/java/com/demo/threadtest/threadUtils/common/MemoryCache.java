package com.topcheer.queryinfo.service.ThreadUtils.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryCache {

    public static Map<String, Object> orderBeanMap = new ConcurrentHashMap<>();

}
