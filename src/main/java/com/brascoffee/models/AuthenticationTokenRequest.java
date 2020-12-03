package com.brascoffee.models;

public class AuthenticationTokenRequest {

	private String token;
	

	public AuthenticationTokenRequest() {
	}

	public AuthenticationTokenRequest(String token) {
		super();
		this.token = token;
		
	}

	public String getToken() {
		return token.split(" ")[1];
	}

	public void setUToken(String token) {
		this.token = token;
	}
	

}
