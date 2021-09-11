package com.jf.common.utils.result;

import com.jf.common.utils.meta.enums.GlobalErrorCodeEnum;
import lombok.Data;

/**
 * @author 江峰
 */
@Data
public class PageQueryResponse<T> extends BaseResult<T> {

    private static final long serialVersionUID = -2653797671530890726L;

    // 总记录数
    private Integer total;

    // 每页记录数
    private Integer pageSize;

    // 总页数
    private Integer totalPages;

    // 当前页码
    private Integer currPage;

    public PageQueryResponse() {

    }

    public static <T> PageQueryResponse<T> success(T data, Integer total) {
        PageQueryResponse<T> result = new PageQueryResponse<>();
        result.setData(data);
        result.setTotal(total);
        result.setResultCode(GlobalErrorCodeEnum.SUCCESS);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> PageQueryResponse<T> failure() {
        PageQueryResponse<T> result = new PageQueryResponse<>();
        result.setResultCode(GlobalErrorCodeEnum.SERVER_BUSY);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

}
