package cn.topcheer.service.serialNum;

import org.json.JSONObject;

import java.io.*;
import java.util.Optional;

public class DBUtil {
    public static long getVersionFromDB(String bType, String path) {

        String str = Optional.ofNullable(readTxt(path)).orElse("");
        if ("".equals(str)) {
            str = "{}";
        }
        JSONObject object = new JSONObject(str);
        if (!object.has(bType)) {
            object.put(bType, 1);
        }
        writeTxt(path, object.toString());
        return object.getLong(bType);
    }

    public static void updateVersionFromDB(String bType, long version, String path) {

        String str = readTxt(path);
        if ("".equals(str)) {
            str = "{}";
        }
        JSONObject object = new JSONObject(str);

        object.put(bType, version);

        writeTxt(path, object.toString());
    }

    public static String readTxt(String txtPath) {
        synchronized (DBUtil.class) {
            File file = new File(txtPath);
            if (file.isFile() && file.exists()) {
                BufferedReader bufferedReader = null;
                InputStreamReader inputStreamReader = null;
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer sb = new StringBuffer();
                    String text = null;
                    while ((text = bufferedReader.readLine()) != null) {
                        sb.append(text);
                    }

                    return sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }
            //
        }
    }

    /**
     * 使用FileOutputStream来写入txt文件
     *
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath, String content) {

        //FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error " + e.getMessage());
        }


       /* FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if(!file.exists()){
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
