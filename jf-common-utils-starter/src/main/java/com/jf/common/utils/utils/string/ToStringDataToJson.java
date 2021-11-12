package com.jf.common.utils.utils.string;

/**
 * lombok  toString转化json
 */
public class ToStringDataToJson {

    public static void main(String[] args) {
        // String str = "111";
        String str = "id=1, name=江峰";
        System.out.println(StringJsonUtils.toJsonStringByOneObject(str));
    }
}