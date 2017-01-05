package com.qlp.core.entity;

/**
 * 返回结果实体(简单版)
 * @author qlp
 *
 */
public class MsgInfo extends BaseEntity{
	
	private static final long serialVersionUID = -1972413320769235514L;
	
	private boolean isSuccess;	//成功标识
	private String msg;			//提示信息
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public MsgInfo(boolean isSuccess) {
		super();
		this.isSuccess = isSuccess;
	}

	public MsgInfo(boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}

}
