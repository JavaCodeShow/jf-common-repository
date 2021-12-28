package com.jf.common.utils.utils.file;

import java.io.*;

public class FileUtils {

    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     */
    public static void readToBuffer(StringBuffer buffer, String filePath)
            throws IOException {

        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 字符串(文本内容)
     * @throws IOException
     */
    public static String readFileToString(String filePath) {
        StringBuffer sb = new StringBuffer();
        try {
            readToBuffer(sb, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
