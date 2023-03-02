package com.sunlight.common.exception;

public class ExceptionErrorCode {
	
	//登录异常
	public static final String LOGIN_PASSWORD_NOT_CORRECT = "psswordnotcorrect";
	public static final String LOGIN_LOGINNAME_NOT_REGISTER = "loginnamenotregister";
	
	//WECHAT -- 微信操作异常
	public static final String WECHAT_URL_VALIDATE_FAIL = "webchaturlvalidatefail";
	public static final String WECHAT_INIT_FAIL = "webchatinitfail";
	public static final String WECHAT_DECRYPT_FAIL = "webdecryptfail";
	
	
	//MYBATIS -- 数据库操作异常
	public static final String MYBATIS_INSERT_USEROPERSESSION = "saveUserOperationSession";
	
}
