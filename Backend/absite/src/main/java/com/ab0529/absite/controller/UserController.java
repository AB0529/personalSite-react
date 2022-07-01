package com.ab0529.absite.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class UserController {
	/*
	* DELETE USER
	* Handles user deletion
	* ROLE_USER can only delete themselves
	* ROLE_USER_DELETE or ROLE_ADMIN can delete any user
	*/
}
