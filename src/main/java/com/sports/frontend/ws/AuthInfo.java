package com.sports.frontend.ws;

public class AuthInfo {
	
	private String user;
	private String password;
	
	
	public AuthInfo(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}


	public String getUser() {
		return user;
	}


	public String getPassword() {
		return password;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
