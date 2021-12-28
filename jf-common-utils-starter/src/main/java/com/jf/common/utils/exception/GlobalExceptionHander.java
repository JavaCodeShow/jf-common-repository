package com.jf.common.utils.exception;

import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import com.jf.common.utils.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理全局异常
 *
 * @author 江峰
 * @date 2020/6/29 16:28
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHander {

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public BaseResult exceptionHandler(BizException e) {

        log.error("发生业务异常！errorCode = [{}], errorMsg = [{}], 原因是:",
                e.getErrorCode(), e.getErrorMsg(), e);
        return BaseResult.fail(e.getErrorCode(), e.getErrorMsg());

    }

    /**
     * 处理空指针的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public BaseResult exceptionHandler(NullPointerException e) {

        log.error("发生空指针异常！原因是:", e);
        return BaseResult.fail(GlobalErrorCodeEnum.SERVER_ERROR);

    }

    /**
     * 处理表单异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResult exceptionHandler(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        // 判断是否有错误的消息，如果存在就是用异常中的消息，没有就是用默认的消息
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                // 这里列出了全部的错误消息，一般只取第一条即可。
                FieldError fieldError = (FieldError) allErrors.get(0);
                log.error("参数校验不通过，请检查请求参数! 原因是: field = [{}], message = [{}]",
                        fieldError.getField(), fieldError.getDefaultMessage());
                return BaseResult.fail(
                        GlobalErrorCodeEnum.PARAMS_ERROR.getCode(),
                        "字段：" + fieldError.getField() + "; 原因："
                                + fieldError.getDefaultMessage());
            }
        }
        log.error("参数校验不通过，请检查请求参数!");
        return BaseResult.fail(GlobalErrorCodeEnum.PARAMS_ERROR);

    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResult exceptionHandler(Exception e) {

        log.error("未知异常！原因是:", e);
        return BaseResult.fail(GlobalErrorCodeEnum.SERVER_ERROR);

    }
}