package com.el2.eflexs.auth.model;

import org.springframework.stereotype.Component;

@Component
public class TokenRequest {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
