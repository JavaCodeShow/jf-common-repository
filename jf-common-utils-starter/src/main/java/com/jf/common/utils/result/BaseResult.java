package com.jf.common.utils.result;

import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述: 返回统一数据结构
 *
 * @author: 江峰
 * @create: 2021-03-19 17:22
 * @since: 2.20.1.1
 */
@Data
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -2893410163392884394L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 成功数据
     */
    private T data;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String msg;

    /**
     * 成功返回，无数据
     */
    public BaseResult() {
    }

    /**
     * 成功返回，有数据
     *
     * @param data
     */
    private BaseResult(T data) {
        this.data = data;
        this.setResultCode(GlobalErrorCodeEnum.SUCCESS);
        this.setSuccess(Boolean.TRUE);
    }

    /**
     * 成功返回，有数据，自定义code和msg
     *
     * @param data
     * @param code
     * @param msg
     */
    private BaseResult(T data, String code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    /**
     * 返回成功，无数据
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<T>(null);
    }

    /**
     * 返回成功，有数据
     */
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(data);
    }

    /**
     * 返回成功，有数据，自定义返回code
     */
    public static <T> BaseResult<T> success(T data, String code) {
        return new BaseResult<T>(data, code,
                GlobalErrorCodeEnum.SUCCESS.getMessage(), Boolean.TRUE);
    }

    /**
     * 返回成功，有数据，自定义返回的code和返回的msg
     */
    public static <T> BaseResult<T> success(T data, String code, String msg) {
        return new BaseResult<T>(data, code, msg, Boolean.TRUE);
    }

    /**
     * 失败返回,默认 code 和 msg
     */
    public static BaseResult fail() {
        return fail(GlobalErrorCodeEnum.SERVER_BUSY.getCode(),
                GlobalErrorCodeEnum.SERVER_BUSY.getMessage());
    }

    /**
     * 失败返回,自定义 msg
     */
    public static BaseResult fail(String msg) {
        return fail(GlobalErrorCodeEnum.SERVER_BUSY.getCode(), msg);
    }

    /**
     * 失败返回,自定义 code和msg
     */
    public static BaseResult fail(String code, String msg) {
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    /**
     * 失败返回,自定义选择ResultCodeEnum
     */
    public static BaseResult fail(GlobalErrorCodeEnum resultCodeEnum) {
        return fail(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

    protected void setResultCode(GlobalErrorCodeEnum code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    /**
     * 获取结果（成功还是失败）
     */
    public boolean getSuccess() {
        return this.success;
    }

}