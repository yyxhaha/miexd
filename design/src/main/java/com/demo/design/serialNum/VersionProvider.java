package com.demo.design.serialNum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VersionProvider {


    private static Map<String, Version> container = new ConcurrentHashMap<>();

    public static String getVersion(String btype) {
        return "";
    }

    public static String getVersionSoftCache(String btype, String path) {
        if (!container.containsKey(btype)) {
            synchronized (VersionProvider.class) {
                if (!container.containsKey(btype)) {
                    Version version = new Version(btype, new ClearVersionObserver());
                    container.put(btype, version);
                }
            }
        }
        return container.get(btype).getVersion(path);
    }

    public static String getVersionCache(String btype, String path) {

        if (!container.containsKey(btype)) {
            synchronized (VersionProvider.class) {
                if (!container.containsKey(btype)) {
                    Version version = new Version(btype, new CarryOnVersionObserver());
                    container.put(btype, version);
                }
            }
        }
        return container.get(btype).getVersion(path);
    }

    public static void clearVersion(String btype) {
        container.remove(btype);
    }
}
