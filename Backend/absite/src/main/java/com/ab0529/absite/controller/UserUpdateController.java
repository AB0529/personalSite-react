package com.ab0529.absite.controller;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.CustomUserDetails;
import com.ab0529.absite.model.UserUpdateRequest;
import com.ab0529.absite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
public class UserUpdateController {
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private final ResponseEntity<?> ERR_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: user not found").asResponseEntity();
	private final ResponseEntity<?> ERR_NO_ID = new ApiResponse(HttpStatus.NOT_FOUND, "error: no user id provided").asResponseEntity();
	private final ResponseEntity<?> UPDATE_SUCCESS = new ApiResponse(HttpStatus.OK, "successfully updated user").asResponseEntity();
	private final ResponseEntity<?> ERR_UNAUTHORIZED = new ApiResponse(HttpStatus.FORBIDDEN, "error: unauthorized access").asResponseEntity();

	Logger logger = LoggerFactory.getLogger(UserController.class);
	/*
	* UPDATE USER
	* Updates user with new user object
	* ROLE_ADMIN or USER_EDIT_AUTHORITY can update any user
	* ROLE_USER can only update themselves
	* TODO: add role updating
	*/

	@PatchMapping("/admin/update")
	@PreAuthorize("hasRole('ADMIN') OR hasAuthority('USER_EDIT')")
	public ResponseEntity<?> adminUpdateUserPart(@RequestBody UserUpdateRequest newUser) {
		logger.info("PATCH /api/users/admin/update");
		if (newUser.getId() == null)
			return ERR_NO_ID;
		return userUpdate(newUser);
	}

	@PatchMapping("/update")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> userUpdatePart(@RequestBody UserUpdateRequest newUser, Authentication authentication) {
		logger.info("PATCH /api/users/update");
		if (newUser.getId() == null)
			return ERR_NO_ID;
		// Make sure user is themselves
		CustomUserDetails customUserDetails = (CustomUserDetails)  authentication.getPrincipal();

		if (customUserDetails.getId() != newUser.getId())
			return ERR_UNAUTHORIZED;

		return userUpdate(newUser);
	}

	// Helper method to update the user object
	private ResponseEntity<?> userUpdate(UserUpdateRequest newUser) {
		try {
			// Make sure user exists
			Optional<User> dbUser = userService.findById(newUser.getId());

			if (dbUser.isEmpty())
				return ERR_NOT_FOUND;

			User user = dbUser.get();

			// Partial updates
			if (newUser.getUsername() != null)
				user.setUsername(newUser.getUsername());
			if (newUser.getPassword() != null)
				user.setPassword(passwordEncoder.encode(newUser.getPassword()));
			if (newUser.getEmail() != null)
				user.setEmail(newUser.getEmail());
			if (newUser.getFirstName() != null)
				user.setFirstName(newUser.getFirstName());
			if (newUser.getLastName() != null)
				user.setLastName(newUser.getLastName());

			userService.save(user);
			return UPDATE_SUCCESS;
		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: "+e.getMessage()).asResponseEntity();
		}
	}
}
