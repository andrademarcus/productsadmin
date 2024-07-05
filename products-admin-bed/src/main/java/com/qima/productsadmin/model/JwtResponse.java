package com.qima.productsadmin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtResponse(String token) {
		this.accessToken = token;
	}
}
