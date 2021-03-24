package com.jf.common.utils.result;

import com.jf.common.utils.meta.enums.ResultCodeEnum;

import lombok.Data;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-19 23:47:38
 * @since
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
		result.setResultCode(ResultCodeEnum.SUCCESS);
		result.setSuccess(Boolean.TRUE);
		return result;
	}

	public static <T> PageQueryResponse<T> failure() {
		PageQueryResponse<T> result = new PageQueryResponse<>();
		result.setResultCode(ResultCodeEnum.ERROR);
		result.setSuccess(Boolean.FALSE);
		return result;
	}

}
