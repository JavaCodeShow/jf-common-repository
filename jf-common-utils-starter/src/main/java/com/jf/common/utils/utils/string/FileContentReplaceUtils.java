package com.jf.common.utils.utils.string;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 江峰
 * @date 2022/7/14 16:26
 */
public class FileContentReplaceUtils {
    //存储要修改的内容
    private final Map<String, String> map;

    public FileContentReplaceUtils(Map<String, String> map) {
        this.map = map;
    }

    public static void main(String[] args) {
        //读取指定文件夹下的所有文件
        //给我你的目录文件夹路径
        String filepath = "F:\\opt";
        Map<String, String> map = new HashMap<>();
        //把需要替换的文本直接复制到 "" 内就行，编译器会自动添加转义符之类的，否则手动替换拼接会累死
        map.put("MoreFungroup@qadb_ro", "0MEt7p@#kc7zY1wVR9");
        map.put("dev_acs_rw%123", "0MEt7p@#kc7zY1wVR9");
        // map.put("要修改的内容2;", "替换后的内容2");
        new FileContentReplaceUtils(map).refreshFileList(filepath);

    }

    public void refreshFileList(String filepath) {
        refreshFileList(new File(filepath));
    }

    public void refreshFileList(File filepath) {
        if (!filepath.exists()) {
            System.out.println("此路径不存在");
            return;
        }
        File[] fileList = filepath.listFiles();
        if (fileList == null || fileList.length < 1) {
            System.out.println("空文件夹");
            return;
        }
        for (File file : fileList) {
            if (file.isDirectory() && file.getName().contains("configs")) {
                //如果是文件夹递归扫描
                refreshFileList(file);
                continue;
            }
            if (file.getName().contains("properties")) {
                changeFile(file);
            }
        }
    }

    /**
     * 修改文件
     *
     * @param file
     */
    private void changeFile(File file) {
        String s = null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis));) {

            //之所以用BufferedReader,而不是直接用BufferedInputStream读取,是因为BufferedInputStream是InputStream的间接子类,
            //InputStream的read方法读取的是一个byte,而一个中文占两个byte,所以可能会出现读到半个汉字的情况,就是乱码.
            //BufferedReader继承自Reader,该类的read方法读取的是char,所以无论如何不会出现读个半个汉字的.
            StringBuilder result = new StringBuilder();
            while (reader.ready()) {
                result.append((char) reader.read());
            }
            s = result.toString();
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> mapKey : entries) {
                if (s.contains(mapKey.getKey())) { //判断当前行是否存在想要替换掉的字符
                    s = s.replace(mapKey.getKey(), mapKey.getValue());//替换为你想替换的内容
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        writerFile(s, file);

    }

    /**
     * 写入文件
     *
     * @param s
     * @param file
     */
    public void writerFile(String s, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(s.getBytes());
            System.out.println("文件名：" + file.getName() + "  ==> 文件修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件名：" + file.getName() + "  ==> 文件修改成功！");
        }
    }

}

