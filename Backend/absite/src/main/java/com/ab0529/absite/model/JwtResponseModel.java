package com.ab0529.absite.model;

import lombok.*;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import java.util.List;

@Getter
@Setter
public class JwtResponseModel {
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private List<String> roles;
	private String token;

	public JwtResponseModel(String username, String email, String firstName, String lastName, List<String> roles, String token) {
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.token = token;
	}
}
