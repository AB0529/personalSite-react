package com.ab0529.absite.controller;

import com.ab0529.absite.config.jwt.JwtUtils;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserDataController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping()
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> getUserData(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return new ApiResponse(HttpStatus.OK, "User found!", userDetails).responseEntity();
	}

	@GetMapping("/all/{max}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllUsersLimited(@PathVariable int max) {
		Iterable<User> users = userRepository.findAllLimited(max);

		return new ApiResponse(HttpStatus.OK, "Users found", users).responseEntity();
	}
}
