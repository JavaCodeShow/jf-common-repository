package com.jf.common.utils.meta.enums;

import lombok.Getter;

/**
 * 描述: 状态码
 *
 * @author: 江峰
 * @create: 2021-03-19 18:36
 * @since: 2.20.1.1
 */
@Getter
public enum GlobalErrorCodeEnum implements BaseResponseInfoInterface {


    SUCCESS("200", "成功"),
    PARAMS_NOT_MATCH("400", "参数校验不通过，请检查请求参数!"),
    NOT_FOUND("404", "未找到该资源!"),
    SERVER_BUSY("500", "系统繁忙，请稍后重试!"),
    RESUBMIT("600", "操作频繁，请稍后重试!"),
    NOT_GET_LOCK("601", "没有获取到锁!"),
    RPC_TIME_OUT("700", "请求超时");


    private String code;

    private String message;

    GlobalErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取该code对应的枚举
     *
     * @param code
     * @return
     */
    public static GlobalErrorCodeEnum getEnumByCode(String code) {
        for (GlobalErrorCodeEnum item : GlobalErrorCodeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据code获取该code对应的枚举的描述
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(String code) {
        for (GlobalErrorCodeEnum item : GlobalErrorCodeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.message;
            }
        }
        return null;
    }

    /**
     * 根据描述获取对应的枚举
     *
     * @param msg
     * @return
     */
    public static GlobalErrorCodeEnum getEnumByMessage(String msg) {
        for (GlobalErrorCodeEnum item : GlobalErrorCodeEnum.values()) {
            if (item.getMessage().equals(msg)) {
                return item;
            }
        }
        return null;
    }
}