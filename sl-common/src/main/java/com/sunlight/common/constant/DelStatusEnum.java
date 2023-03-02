package com.sunlight.common.constant;
public enum DelStatusEnum {
	
	UnDelete(0),//未删除
	Delete(1);//数据被删除标志
	private int value;
	private DelStatusEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
