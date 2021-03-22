package com.jf.common.utils.result;

import java.io.Serializable;

import com.jf.common.common.meta.ResultCodeEnum;
import com.jf.common.utils.time.LocalDateTimeUtil;

import lombok.Data;

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
	 * 服务器当前时间 格式为:yyyy-MM-dd HH:mm:ss
	 */
	private String time = LocalDateTimeUtil.getLocalDateTimeStr();

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
		this.setResultCode(ResultCodeEnum.SUCCESS);
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
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> BaseResult<T> success() {
		return new BaseResult<T>(null);
	}

	/**
	 * 返回成功，有数据
	 * 
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> BaseResult<T> success(T data) {
		return new BaseResult<T>(data);
	}

	/**
	 * 返回成功，有数据，自定义返回code
	 * 
	 * @param data
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> BaseResult<T> success(T data, String code) {
		return new BaseResult<T>(data, code,
				ResultCodeEnum.SUCCESS.getMessage(), Boolean.TRUE);
	}

	/**
	 * 返回成功，有数据，自定义返回的code和返回的msg
	 * 
	 * @param data
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> BaseResult<T> success(T data, String code, String msg) {
		return new BaseResult<T>(data, code, msg, Boolean.TRUE);
	}

	protected void setResultCode(ResultCodeEnum code) {
		this.code = code.getCode();
		this.msg = code.getMessage();
	}

	/**
	 * 失败返回,自定义 msg
	 *
	 * @param msg
	 * @return
	 */
	public BaseResult fail(String msg) {
		return fail(ResultCodeEnum.ERROR.getCode(), msg);
	}

	/**
	 * 失败返回,自定义 code和msg
	 *
	 * @param code
	 * @param msg
	 * @return
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
	 *
	 * @param resultCodeEnum
	 * @return
	 */
	public static BaseResult fail(ResultCodeEnum resultCodeEnum) {
		return fail(resultCodeEnum.getCode(), resultCodeEnum.getMessage());
	}

	/**
	 * 获取结果（成功还是失败）
	 * 
	 * @return
	 */
	public boolean getSuccess() {
		return this.success;
	}

}