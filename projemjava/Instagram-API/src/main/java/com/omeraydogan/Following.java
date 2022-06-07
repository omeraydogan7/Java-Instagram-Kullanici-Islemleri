package com.omeraydogan;

import java.io.Serializable;

public class Following implements Serializable {

	private static final long serialVersionUID = 1L;

	private long pk;
	
	private String username;
	
	private String fullname;
	
	public Following()
	{
		
	}

	public Following(long pk, String username, String fullname) {
		super();
		this.pk = pk;
		this.username = username;
		this.fullname = fullname;
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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	











}
