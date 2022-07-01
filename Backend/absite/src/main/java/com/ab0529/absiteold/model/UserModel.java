package com.ab0529.absiteold.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserModel {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
}
