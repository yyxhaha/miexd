package com.demo.threadtest.classLoader.service;



import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader{
    private final static Path d= Paths.get("d:","classloader1");
    private final Path classDir;
    public MyClassLoader(){
        super();
        this.classDir=d;
    }

    public MyClassLoader(Path classDir){
        super();
        this.classDir=classDir;
    }

    public MyClassLoader(String classDir,ClassLoader parrent){
        super(parrent);
        this.classDir=Paths.get(classDir);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classtypes=this.readClassBytes(name);
        if(classtypes==null||classtypes.length==0){
            throw new ClassNotFoundException("cannot find class"+name);
        }

        return this.defineClass(name,classtypes,0,classtypes.length);
    }
    private byte[] readClassBytes(String name) throws ClassNotFoundException {
        String path=name.replace(".","/");
        Path fullPath = classDir.resolve(Paths.get(path + ".class"));
        if(!fullPath.toFile().exists()){
            throw new ClassNotFoundException("the class "+name+"not found");
        }
        try(ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream()){
            Files.copy(fullPath,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception ex){
            throw new ClassNotFoundException("load class "+name+" error:", ex);
        }
    }

    @Override
    public String toString() {
        return "My ClassLoader";
    }
}
