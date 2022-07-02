package com.ab0529.absite.controller;

import com.ab0529.absite.entity.Authority;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.EAuthority;
import com.ab0529.absite.service.AuthorityService;
import com.ab0529.absite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users/")
@Slf4j
public class UserAuthorityController {
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserService userService;

	private final ResponseEntity<?> ERR_UNAUTHORIZED = new ApiResponse(HttpStatus.FORBIDDEN, "error: unauthorized access").asResponseEntity();
	private final ResponseEntity<?> ERR_USER_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: user not found").asResponseEntity();
	private final ResponseEntity<?> ERR_AUTHORITY_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: authority not found").asResponseEntity();
	private final ResponseEntity<?> ERR_AUTHORITY_ALREADY_ADDED = new ApiResponse(HttpStatus.NOT_FOUND, "error: authority exists on user").asResponseEntity();

	/*
	* ADD AUTHORITY
	* Adds an authority to any user
	* Must have AUTHORITY_ADMIN or USER_EDIT or USER_ROLE_ADD or USER_ROLE_EDIT
	*/
	@PutMapping("/authorities/add/{id}/{name}")
	@PreAuthorize("hasRole('ADMIN') OR hasAnyAuthority('USER_AUTHORITY_ADD', 'USER_EDIT', 'USER_AUTHORITY_EDIT')")
	public ResponseEntity<?> addAuthority(@PathVariable Long id, @PathVariable String name) {
		log.info("PUT /api/users/authorities/add/"+id+"/"+name);
		try {
			// Make sure authority exists
			EAuthority authorityE = EAuthority.valueOf(name.toUpperCase());
			// Make sure authority is not ADMIN_ROLE_ADD
			if (authorityE.equals(EAuthority.ADMIN_ROLE_ADD))
				return ERR_UNAUTHORIZED;

			return addAuthorityHelper(id, authorityE);
		} catch (IllegalArgumentException e) {
			return ERR_AUTHORITY_NOT_FOUND;
		}
	}

	// Helper method to add authority to user
	public ResponseEntity<?> addAuthorityHelper(Long userid, EAuthority authority) {
		try {
			Optional<User> user = userService.findById(userid);
			if (user.isEmpty())
				return ERR_USER_NOT_FOUND;

			// Add authority
			Authority r = authorityService.findByName(authority).orElseThrow();
			userService.addAuthorityToUser(userid, r.getId());
			return new ApiResponse(HttpStatus.OK, "successfully added authority").asResponseEntity();
		}
		catch (Exception e) {
			if (e.getMessage().startsWith("org.hibernate.exception.ConstraintViolationException"))
				return ERR_AUTHORITY_ALREADY_ADDED;

			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}

	/*
	 * REMOVE AUTHORITY
	 * Removes an authority from any user
	 * Must have AUTHORITY_ADMIN or USER_EDIT or USER_ROLE_REMOVE, USER_ROLE_EDIT
	 */
	@DeleteMapping("/authorities/remove/{id}/{name}")
	@PreAuthorize("hasRole('ADMIN') OR hasAnyAuthority('USER_AUTHORITY_REMOVE', 'USER_EDIT', 'USER_AUTHORITY_EDIT')")
	public ResponseEntity<?> removeAuthority(@PathVariable Long id, @PathVariable String name) {
		log.info("DELETE /api/users/authorities/remove/"+id+"/"+name);
		try {
			// Make sure authority exists
			EAuthority authorityE = EAuthority.valueOf(name.toUpperCase());

			return removeAuthorityHelper(id, authorityE);
		} catch (IllegalArgumentException e) {
			return ERR_AUTHORITY_NOT_FOUND;
		}
	}

	// Helper method to remove authority from user
	public ResponseEntity<?> removeAuthorityHelper(Long userid, EAuthority authority) {
		try {
			Optional<User> user = userService.findById(userid);
			if (user.isEmpty())
				return ERR_USER_NOT_FOUND;

			// Remove authority
			Authority r = authorityService.findByName(authority).orElseThrow();
			userService.removeAuthorityFromUser(userid, r.getId());
			return new ApiResponse(HttpStatus.OK, "successfully removed authority").asResponseEntity();
		}
		catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}
}
