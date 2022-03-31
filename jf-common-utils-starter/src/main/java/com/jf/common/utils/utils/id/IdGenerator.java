package com.jf.common.utils.utils.id;

/**
 * ID发号器：生成唯一的并且趋势自增的ID
 *
 * @author 江峰
 * @date 2022/3/4 17:13
 */
public class IdGenerator {

    /**
     * 获取一个唯一的并且趋势自增的ID
     */
    public static String getId() {
        return new ObjectId().toString();
    }

}
