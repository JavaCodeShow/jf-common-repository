package com.jf.common.utils.utils.string;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class StringJsonUtils {


    /**
     * 转换单个对象的字符串为json
     * 不支持数组
     * <p>
     * User user = new User(1, "江峰");
     * System.out.println(user);
     */
    public static <T> Object toJsonStringByOneObject(String str) {
        if (StringUtils.isEmpty(str)) {
            return "Parameter Cannot Be Empty";
        }
        str = str.replace(")", "}");
        while (str.contains("(")) {
            int i = str.indexOf("(");
            int i1 = str.lastIndexOf("=", i);
            String str1 = str.substring(0, i1 + 1);
            String str4;
            str4 = str.substring(i1 + 1, i1 + 2);
            String str2 = "{";
            String str3 = str.substring(i + 1);
            String str6 = "";
            String str5 = str.substring(i1, i);
            int i2 = str5.indexOf("},");
            if (i2 > 0) {
                str6 = str5.substring(1, i2 + 2);
            }
            str = str4.equals("[") ? str1 + str4 + str6 + str2 + str3 : str1 + str6 + str2 + str3;
        }
        if (!str.startsWith("{")) {
            str = "{" + str + "}";
        }
        str = str.replace(" ", "")
                .replace("=", "\"=\"")
                .replace("\"{", "{\"")
                .replace("}\"", "}")
                .replace("}", "\"}")
                .replace("\"}\"}", "\"}}")
                .replace("\"[{", "[{\"")
                .replace("]\"", "]");

        str = "\"" + str.replace("=", ":");
        if ("\"{".equals(str.substring(0, 2))) {
            str = "{\"" + str.substring(2);
        } else {
            str = str.substring(str.indexOf(":") + 1);
        }
        str = str.replace("\"null\"", "null");
        List<String> list = Arrays.asList(str.split(","));
        String s1 = list.get(0);
        StringBuilder s0 = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).contains(":")) {
                s0.append("\",\"").append(list.get(i));
            } else {
                s0.append(",").append(list.get(i));
            }
        }
        String s = (s1 + s0).replace("\"[{", "[{\"")
                .replace("}]\"", "\"}]")
                .replace("}\"", "\"}")
                .replace("\"{", "{\"")
                .replace("\"\"}", "\"}")
                .replace("{\"\"", "{\"");
        T json = JSONObject.parseObject(s, (Class<T>) null);
        return null == null ? s : json;
    }
}
