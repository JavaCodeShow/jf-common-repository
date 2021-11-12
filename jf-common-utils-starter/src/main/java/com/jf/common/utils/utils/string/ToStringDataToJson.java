package com.jf.common.utils.utils.string;

import com.jf.common.utils.utils.file.FileUtils;

import java.text.ParseException;

/**
 * lombok  toString转化json
 */
public class ToStringDataToJson {

    public static void main(String[] args) throws ParseException {

        // String str = "(id=1, name=null)";

        String str = FileUtils.readFileToString("E:\\code\\jf-common-repository\\jf-common-utils-starter\\src\\main\\java\\com\\jf\\common\\utils\\utils\\string\\string字符串");

        // 对象：直接复制进文本即可
        // 数组：如：[User(id=1, name=张三), User(id=2, name=李四)]  需改为：(xxx=[User(id=1, name=张三), User(id=2, name=李四)])
        System.out.println(StringToJsonUtils.toJSONString(str));

    }
}