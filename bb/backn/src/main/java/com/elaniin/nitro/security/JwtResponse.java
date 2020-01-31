package com.elaniin.nitro.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Getter
@NoArgsConstructor
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 4L;
	private String token;
	private String username;
	private Date expiration;
	private String type;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JwtResponse(String token, String username, Date expiration, String type) {
		super();
		this.token = token;
		this.username = username;
		this.expiration = expiration;
		this.type = type;
	}

}
