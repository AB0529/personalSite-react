package com.ab0529.absite.controller;

import com.ab0529.absite.component.JwtTokenUtil;
import com.ab0529.absite.entity.ERole;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.LoginRequest;
import com.ab0529.absite.model.RegisterRequest;
import com.ab0529.absite.service.JwtUserDetailsService;
import com.ab0529.absite.service.RoleService;
import com.ab0529.absite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	/**
	 * LOGIN
	 * Handles login authentication
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		final ResponseEntity<?> ERR_USER_DISABLED = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: user is disabled").asResponseEntity();
		final ResponseEntity<?> ERR_BAD_CREDENTIALS = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: bad login").asResponseEntity();

		try {
			logger.debug("POST /api/auth/login");
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			// Load user
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			// Generate JwtToken with ip claim
			final String token = jwtTokenUtil.generateTokenWithRemoteAddrClaim(userDetails, request.getRemoteAddr());
			// Return token if authentication was successful
			return new ApiResponse(HttpStatus.OK, "authentication successful", token).asResponseEntity();
		} catch (DisabledException e) {
			return ERR_USER_DISABLED;
		} catch (BadCredentialsException e) {
			return ERR_BAD_CREDENTIALS;
		}
	}

	/*
	* REGISTER
	* Handles user creation
	*/
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		final ResponseEntity<?> ERR_USERNAME_TAKEN = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: username is taken").asResponseEntity();
		final ResponseEntity<?> ERR_EMAIL_TAKEN = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: email is taken").asResponseEntity();
		final ResponseEntity<?> ERR_ROLE_NOT_FOUND = new ApiResponse(HttpStatus.NOT_FOUND, "error: role not found").asResponseEntity();

		try {
			logger.debug("POST /api/auth/register");
			// Make sure username and email are not taken
			if (userService.existsByUsername(registerRequest.getUsername()))
				return ERR_USERNAME_TAKEN;
			else if (userService.existsByEmail(registerRequest.getEmail()))
				return ERR_EMAIL_TAKEN;

			// Assign user role
			Optional<Role> userRole = roleService.findByName(ERole.ROLE_USER);
			// Make sure role exists
			if (userRole.isEmpty())
				return ERR_ROLE_NOT_FOUND;

			// Build the new user
			User user = new User(
					registerRequest.getUsername(),
					passwordEncoder.encode(registerRequest.getPassword()),
					registerRequest.getFirstName(),
					registerRequest.getLastName(),
					registerRequest.getEmail()
			);

			// Add role to user
			user.addRole(userRole.get());
			userService.save(user);

			return new ApiResponse(HttpStatus.OK, "registration successful", user).asResponseEntity();
		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "error: " + e.getMessage()).asResponseEntity();
		}
	}
}
