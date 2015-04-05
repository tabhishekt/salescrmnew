package com.propmgr.resource;

import java.sql.Clob;

public class RoleResource {
	private long id;
	private String name;
	private String description;
	private boolean admin;
	
	public RoleResource(long id, String name, String description, boolean admin) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.admin = admin;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isAdmin() {
		return admin;
	}
}
