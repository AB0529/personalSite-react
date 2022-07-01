package com.ab0529.absite.controller;

import com.ab0529.absite.entity.ERole;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.service.RoleService;
import com.ab0529.absite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users/")
public class UserRoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	private final ResponseEntity<?> ERR_UNAUTHORIZED = new ApiResponse(HttpStatus.FORBIDDEN, "error: unauthorized access").asResponseEntity();
	private final ResponseEntity<?> ERR_USER_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: user not found").asResponseEntity();
	private final ResponseEntity<?> ERR_ROLE_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: role not found").asResponseEntity();

	Logger logger = LoggerFactory.getLogger(UserController.class);

	/*
	* ADMIN ADD ROLE
	* Adds a role to any user
	* Must have ROLE_ADMIN or USER_EDIT_AUTHORITY or USER_ROLE_ADD_AUTHORITY
	*/
	@PutMapping("/admin/roles/add/{id}/{name}")
	@PreAuthorize("hasRole('ADMIN') OR hasAuthority('USER_ROLE_ADD')")
	public ResponseEntity<?> adminAddRole(@PathVariable Long id, @PathVariable String name) {
		logger.info("PUT /api/users/admin/roles/add/"+id+"/"+name);
		try {
			// Make sure role exists
			ERole roleE = ERole.valueOf(name.toUpperCase());
			// Make sure role is not ROLE_ADMIN
			if (roleE.equals(ERole.ROLE_ADMIN))
				return ERR_UNAUTHORIZED;

			return addRoleHelper(id, roleE);
		} catch (IllegalArgumentException e) {
			return ERR_ROLE_NOT_FOUND;
		}
	}

	// Helper method to add role to user
	public ResponseEntity<?> addRoleHelper(Long userid, ERole role) {
		try {
			Optional<User> user = userService.findById(userid);
			if (user.isEmpty())
				return ERR_USER_NOT_FOUND;

			// Add role and return user roles as response
			Role r = roleService.findByName(role).orElseThrow();
			userService.addRoleToUser(userid, r.getId());
			return new ApiResponse(HttpStatus.OK, "successfully added role to user").asResponseEntity();
		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}
}
