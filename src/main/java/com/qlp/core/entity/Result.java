package com.qlp.core.entity;

import com.qlp.core.exception.ErrorDetail;

/**
 * 返回结果实体(复杂版)
 * @author qlp
 * @param <T>
 */
public class Result<T> extends BaseEntity{
	
	private static final long serialVersionUID = -4749584860749892798L;
	
	private boolean isSuccess;	//成功标识
	private T result;			//返回结果
	private ErrorDetail error;	//失败时错误信息
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public T getResult() {
		return result;
	}
	
	public void setResult(T result) {
		this.result = result;
	}
	
	public ErrorDetail getError() {
		return error;
	}
	
	public void setError(ErrorDetail error) {
		this.error = error;
	}

	public Result() {
		super();
	}

	public Result(boolean isSuccess, T result) {
		super();
		this.isSuccess = isSuccess;
		this.result = result;
	}

	public Result(boolean isSuccess, T result, ErrorDetail error) {
		super();
		this.isSuccess = isSuccess;
		this.result = result;
		this.error = error;
	}
	
	

}
