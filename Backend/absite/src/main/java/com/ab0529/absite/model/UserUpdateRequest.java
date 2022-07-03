package com.ab0529.absite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserUpdateRequest {
	private Long id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
}
