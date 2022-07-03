package com.ab0529.absite.model;

import lombok.Getter;

@Getter
public class RegisterRequest {
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
}
