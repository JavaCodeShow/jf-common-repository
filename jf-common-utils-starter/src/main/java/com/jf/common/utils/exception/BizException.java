package com.jf.common.utils.exception;

import com.jf.common.utils.meta.enums.BaseResponseInfoInterface;
import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务逻辑异常处理类
 *
 * @author 潇潇暮雨
 * @create 2018-09-30 20:45
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -4011604611008441850L;

    /**
     * 错误码
     */
    protected String errorCode;

    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException(BaseResponseInfoInterface errorResponseInfo) {
        super(errorResponseInfo.getMessage());
        this.errorCode = errorResponseInfo.getCode();
        this.errorMsg = errorResponseInfo.getMessage();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = GlobalErrorCodeEnum.SERVER_BUSY.getCode();
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
