package com.omeraydogan;

import java.io.Serializable;

public class FollowerClass implements Serializable{

	private static final long serialVersionUID = 1L;

	
	
	private long pk;
	
	private String username;
	
	private String fulname;
	
	public FollowerClass()
	{
		
	}

	public FollowerClass(long pk, String username, String fulname) {
		super();
		this.pk = pk;
		this.username = username;
		this.fulname = fulname;
	}

	public long getPk() {
		return pk;
	}

	public void setPk(long pk) {
		this.pk = pk;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFulname() {
		return fulname;
	}

	public void setFulname(String fulname) {
		this.fulname = fulname;
	}
	
	
	
}
