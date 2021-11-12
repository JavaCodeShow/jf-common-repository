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

        System.out.println(StringToJsonUtils.toJSONString(str));

    }
}