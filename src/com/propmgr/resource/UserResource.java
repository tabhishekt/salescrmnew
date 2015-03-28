package com.propmgr.resource;

import java.util.List;


public class UserResource {
	private long id;
	private PersonResource person;
	private AddressResource address;
	private String displayName;
	private String displayAddress;
	private String loginname;
	private String password;
	private List<RoleResource> roles;
	private boolean active;
	
	public UserResource(long id, PersonResource person,
			AddressResource address, String displayName, String displayAddress,
			String loginname, String password, List<RoleResource> roles,
			boolean active) {
		super();
		this.id = id;
		this.person = person;
		this.address = address;
		this.displayName = displayName;
		this.displayAddress = displayAddress;
		this.loginname = loginname;
		this.password = password;
		this.roles = roles;
		this.active = active;
	}
	
	public long getId() {
		return id;
	}
	
	public PersonResource getPerson() {
		return person;
	}
	
	public AddressResource getAddress() {
		return address;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getDisplayAddress() {
		return displayAddress;
	}
	
	public String getLoginname() {
		return loginname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public List<RoleResource> getRoles() {
		return roles;
	}
	
	public boolean isActive() {
		return active;
	}
}
