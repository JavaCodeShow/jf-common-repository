package com.jf.common.utils.result;

import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 江峰
 * @email feng.jiang@marketin.cn
 * @create 2021-03-20 12:37:00
 * @since
 */
@Setter
@ToString
public class PageQueryRequest<T> implements Serializable {

	private static final long serialVersionUID = 3006162444172551276L;

	private static final int DEFAULT_SIZE = 10;

	/**
	 * 每页查多少个
	 */
	private Integer pageSize;

	/**
	 * 当前页码
	 */
	private Integer currPage;

	/**
	 * 查询条件
	 */
	@Valid
	private T data;

	/**
	 * 从哪一行开始查询
	 */
	private Integer startRow;

	/**
	 * 获取每页查询的个数 默认10个
	 *
	 * @return
	 */
	public Integer getPageSize() {
		if (Objects.isNull(this.pageSize)) {
			this.pageSize = DEFAULT_SIZE;
		}
		return this.pageSize >= 1000 ? 1000 : this.pageSize;
	}

	/**
	 * 获取查询的页码 默认是第一页1
	 *
	 * @return
	 */
	public Integer getCurrPage() {

		return Objects.isNull(this.currPage) ? 1 : currPage;
	}

	/**
	 * 获取查询参数
	 *
	 * @return
	 */
	public T getData() {
		return this.data;
	}

	/**
	 * 查询开始行数
	 *
	 * @return
	 */
	public Integer getStartRow() {
		return this.getPageSize() * (this.currPage - 1);
	}

}
