package com.brascoffee.models;

import com.brascoffee.entity.User;

public class AuthenticationTokenResponse {

	private final User user;

	public AuthenticationTokenResponse(User user) {

		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
