package com.qlp.july.core.exception;

/**
 * 自定义异常，分为系统异常和业务异常
 * @author qlp
 *
 */
public class MyException extends RuntimeException{

	private static final long serialVersionUID = -4794106229241147008L;
	
	private ErrorDetail detail;
	
	public MyException(){
		super();
	}
	
	public MyException(String msg){
		super(msg);
	}
	
	public MyException(ErrorDetail detail){
		super(detail.getMsg());
		this.detail = detail;
	}
	
	public MyException(ErrorDetail detail,String msg){
		super(msg);
		this.detail = detail;
	}
	
	public MyException(Throwable throwable){
		super(throwable);
	}
	
	public MyException(Throwable cause,String msg) {
        super(msg, cause);
    }
	
	public MyException(ErrorDetail detail,Throwable cause) {
        super(detail.getMsg(), cause);
        this.detail = detail;
    }
	
	public MyException(ErrorDetail detail,Throwable cause,String msg) {
        super(msg, cause);
        this.detail = detail;
    }
	
	public ErrorDetail getDetail() {
		return detail;
	}

	public void setDetail(ErrorDetail detail) {
		this.detail = detail;
	}

}
