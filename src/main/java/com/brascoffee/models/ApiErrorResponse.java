package com.brascoffee.models;

public class ApiErrorResponse {
	private final String description;

	public ApiErrorResponse(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
