package com.propmgr.resource;

public class CodeTableResource {
	private long id;
	private String code;
	private String name;
	
	public CodeTableResource(long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
