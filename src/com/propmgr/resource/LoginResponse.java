package com.propmgr.resource;

public class LoginResponse {
	private long id;
	private boolean isAdmin;
	private String userName;
	
	public LoginResponse(long id, boolean status, boolean isAdmin, String userName) {
		super();
		this.id = id;
		this.isAdmin = isAdmin;
		this.userName = userName;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public String getUserName() {
		return userName;
	}
}
