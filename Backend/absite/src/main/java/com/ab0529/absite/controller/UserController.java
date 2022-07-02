package com.ab0529.absite.controller;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.CustomUserDetails;
import com.ab0529.absite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
@Slf4j
public class UserController {
	@Autowired
	UserService userService;

	private final ResponseEntity<?> ERR_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: user not found").asResponseEntity();
	private final ResponseEntity<?> ERR_UNAUTHORIZED = new ApiResponse(HttpStatus.FORBIDDEN, "error: unauthorized access").asResponseEntity();

	/*
	* GET ALL USERS LIMIT
	* Retries all users up to limit
	* ROLE_ADMIN or USER_EDIT_AUTHORITY can access this route
	*/
	@GetMapping("/admin/all/{max}")
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('USER_EDIT')")
	public ResponseEntity<?> adminViewAllUsersLimited(@PathVariable int max) {
		log.info("GET /api/users/admin/all/"+max);
		Page<User> users = userService.findAllLimit(max);

		// No users found
		if (users.isEmpty())
			return ERR_NOT_FOUND;

		return new ApiResponse(HttpStatus.OK, "successfully found users", users.toList()).asResponseEntity();
	}

	/*
	* GET USER DETAILS FROM ID
	* Retrieves User from user id
	* ROLE_USER can only access themselves
	* ROLE_ADMIN or USER_EDIT_AUTHORITY can access any user
	*/

	@GetMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN') OR hasAuthority('USER_EDIT')")
	public ResponseEntity<?> adminViewUser(@PathVariable Long id) {
		log.info("GET /api/users/admin/"+id);
		return handleViewUser(id);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> viewUser(@PathVariable Long id, Authentication authentication) {
		log.info("GET /api/users/"+id);
		// Make sure user is themselves
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		if (customUserDetails.getId() != id)
			return ERR_UNAUTHORIZED;

		return handleViewUser(id);
	}

	// Helper method to view user details from id
	private ResponseEntity<?> handleViewUser(Long id) {
		try {
			// Make sure user exists
			Optional<User> user = userService.findById(id);

			if (user.isEmpty())
				return ERR_NOT_FOUND;

			// Return user details
			return new ApiResponse(HttpStatus.OK, "user found successfully", user.get()).asResponseEntity();
		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}

	/*
	* DELETE USER
	* Handles user deletion
	* ROLE_USER can only delete themselves
	* USER_DELETE_AUTHORITY or ROLE_ADMIN can delete any user
	*/
	@DeleteMapping("/admin/delete/{id}")
	@PreAuthorize("hasRole('ADMIN') OR hasAuthority('USER_DELETE')")
	public ResponseEntity<?> deleteAnyUser(@PathVariable Long id) {
		log.info("DELETE /api/users/admin/delete/"+id);
		return handleDeleteUser(id);
	}
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteCurrentUser(@PathVariable Long id, Authentication authentication) {
		log.info("DELETE /api/users/delete/"+id);

		// Make sure user is the owner
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		if (customUserDetails.getId() != id)
			return ERR_UNAUTHORIZED;

		return handleDeleteUser(id);
	}

	/*
	* Helper method to handle user deletion
	*/
	private ResponseEntity<?> handleDeleteUser(Long id) {
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
