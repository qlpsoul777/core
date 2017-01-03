package com.qlp.july.core.exception;

/**
 * 异常细节
 * @author qlp
 *
 */
public interface ErrorDetail {
	
	/**
	 * 错误码
	 * @return
	 */
	public abstract String getCode();
	/**
	 * 错误码描述
	 * @return
	 */
	public abstract String getMsg();
	/**
	 * 判断当前异常是否是系统异常
	 * @return
	 */
	public abstract boolean isSysError();
	
	/**
	 * 系统级异常枚举
	 * @author qlp
	 *
	 */
	public enum SysErrorEnum implements ErrorDetail{
		NULL_EXCEPTION("NullPointerException","空指针异常"),
		DOMAIN_NOT_FOUND("ClassNotFoundException","对象无法获取");
		
		private final String code;
	    private final String msg;
	    
	    SysErrorEnum(String code,String msg){
	    	this.code = code;
	    	this.msg = msg;
	    }
	    
		public String getCode() {
			return this.code;
		}

		public String getMsg() {
			return this.msg;
		}
		
		public boolean isSysError() {
			return true;
		}
		
	}
	
	/**
	 * 业务级异常枚举
	 * @author qlp
	 *
	 */
	public enum BusiErrorEnum implements ErrorDetail{
		INPUT_NOT_EXIST("NULL","输入参数不能为空"),
		INPUT_STATE_ILLEGAL("ILLEGAL_STATE","输入参数状态不合法"), 
		OUTPUT_NOT_FOUND("NOT_FOUND","输出不能为空"),
		OPERATION_ILLEGAL("ILLEGAL_OPERATION","操作不合法");
		
		private final String code;
	    private final String msg;
	    
	    BusiErrorEnum(String code,String msg){
	    	this.code = code;
	    	this.msg = msg;
	    }
	    
		public String getCode() {
			return this.code;
		}

		public String getMsg() {
			return this.msg;
		}
		
		public boolean isSysError() {
			return false;
		}
		
	}

}
