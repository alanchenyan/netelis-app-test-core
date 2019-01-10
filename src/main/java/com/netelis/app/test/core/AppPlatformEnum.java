package com.netelis.app.test.core;

public enum AppPlatformEnum {

	ANDROID("Android","1"),
	IOS("iOS","2");
	
	private String typeName;
	private String typeCode;

	AppPlatformEnum(String typeName, String typeCode) {
		this.typeName = typeName;
		this.typeCode = typeCode;
	}

	public String getypeName() {
		return this.typeName;
	}

	public String getTypeCode() {
		return this.typeCode;
	}
}
