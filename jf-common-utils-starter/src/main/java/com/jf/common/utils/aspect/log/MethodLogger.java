package com.jf.common.utils.aspect.log;

import java.lang.annotation.*;

/**
 * controller层日志打印注解
 *
 * @author 江峰
 * @date 2020/5/18 1:41 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodLogger {

    /**
     * 接口唯一标识Id
     */
    String apiId();

    /**
     * 日志打印类型， 默认请求日志全部打印
     *
     * @return
     */
    LogTypeEnum logType() default LogTypeEnum.FULL;

}
