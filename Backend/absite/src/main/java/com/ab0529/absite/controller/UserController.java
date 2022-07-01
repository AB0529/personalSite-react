package com.ab0529.absite.controller;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.CustomUserDetails;
import com.ab0529.absite.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserController {
	@Autowired
	UserService userService;

	/*
	* DELETE USER
	* Handles user deletion
	* ROLE_USER can only delete themselves
	* ROLE_USER_DELETE or ROLE_ADMIN can delete any user
	*/
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER_DELETE')")
	public ResponseEntity<?> deleteAnyUser(@PathVariable Long id) {
		return handleDeleteUser(id);
	}
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteCurrentUser(@PathVariable Long id, Authentication authentication) {
		final ResponseEntity<?> ERR_UNAUTHORIZED = new ApiResponse(HttpStatus.UNAUTHORIZED, "error: unauthorized deletion").asResponseEntity();

		// Make sure user is the owner
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		if (customUserDetails.getId() != id)
			return ERR_UNAUTHORIZED;

		return handleDeleteUser(id);
	}

	/*
	* Helper method to handle user deletion
	*/
	public ResponseEntity<?> handleDeleteUser(Long id) {
		final ResponseEntity<?> ERR_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: user not found").asResponseEntity();
		final ResponseEntity<?> SUCCESS = new ApiResponse(HttpStatus.OK, "user deleted successfully").asResponseEntity();
		// Make sure user exists
		Optional<User> user = userService.findById(id);

		if (user.isEmpty())
			return ERR_NOT_FOUND;

		// If user exists, delete the user
		try {
			userService.delete(user.get());

			return SUCCESS;
		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}
}
