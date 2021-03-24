package com.jf.common.utils.exception;

import com.jf.common.utils.common.enums.BaseResponseInfoInterface;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 潇潇暮雨
 * @create 2018-09-30 20:45
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -4011604611008441850L;

	/**
	 * 错误码
	 */
	protected String errorCode;

	/**
	 * 错误信息
	 */
	protected String errorMsg;

	public ServiceException(BaseResponseInfoInterface errorResponseInfo) {
		// super(errorResponseInfo.getMessage());
		this.errorCode = errorResponseInfo.getCode();
		this.errorMsg = errorResponseInfo.getMessage();
	}

	public ServiceException(String errorMsg) {
		// super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public ServiceException(String errorCode, String errorMsg) {
		// super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

}
