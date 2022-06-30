package com.ab0529.absite.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserUpdateModel {
	private String username;
	private String oldUsername;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
}
