package com.jf.common.common.meta;

import com.jf.common.exception.BaseResponseInfoInterface;

import lombok.Getter;

/**
 * 描述: 状态码
 *
 * @author: 江峰
 * @create: 2021-03-19 18:36
 * @since: 2.20.1.1
 */
@Getter
public enum ResultCodeEnum implements BaseResponseInfoInterface {

	// @formatter:off

    SUCCESS("200", "成功"),
    ERROR("201", "失败"),
    PARAMS_NOT_MATCH("400", "参数校验不通过，请检查请求参数!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "系统繁忙，请稍后再试!"),
	RESUBMIT("600", "该方法短时间内重复请求!"),
	NOT_GET_LOCK("601", "没有获取到锁!");

    // @formatter:on

	private String code;

	private String message;

	ResultCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 根据code获取该code对应的枚举
	 *
	 * @param code
	 * @return
	 */
	public static ResultCodeEnum getEnumByCode(String code) {
		for (ResultCodeEnum item : ResultCodeEnum.values()) {
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
		for (ResultCodeEnum item : ResultCodeEnum.values()) {
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
	public static ResultCodeEnum getEnumByMessage(String msg) {
		for (ResultCodeEnum item : ResultCodeEnum.values()) {
			if (item.getMessage().equals(msg)) {
				return item;
			}
		}
		return null;
	}
}