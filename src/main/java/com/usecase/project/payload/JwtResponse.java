package com.usecase.project.payload;

public class JwtResponse {
	
	private String token;
	
	private UserDataTransfer user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDataTransfer getUser() {
		return user;
	}

	public void setUser(UserDataTransfer user) {
		this.user = user;
	}

}
