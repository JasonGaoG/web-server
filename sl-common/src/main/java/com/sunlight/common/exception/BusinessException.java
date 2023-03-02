package com.sunlight.common.exception;

/**
 * @package com.bp.maomao.exception
 * @description  用于业务处理的异常
 * @author  Jason Gao
 * @date 2015-6-8下午5:32:07
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 8693766553049819133L;
	private String code;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
