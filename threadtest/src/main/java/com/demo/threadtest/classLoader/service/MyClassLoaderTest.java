package com.demo.threadtest.classLoader.service;

import java.lang.reflect.InvocationTargetException;

public class MyClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        MyClassLoader classLoader=new MyClassLoader();
        Class<?> aClass=classLoader.loadClass("com.example.demo.service.HelloWord");
        System.out.println(aClass.getClassLoader());
/*        Object helloword=aClass.newInstance();
        Method w=aClass.getMethod("welcome");
        String re=(String) w.invoke(helloword);
        System.out.println("res:"+re);*/
    }
}
