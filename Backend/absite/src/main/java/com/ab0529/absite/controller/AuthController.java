package com.ab0529.absite.controller;

import com.ab0529.absite.component.JwtTokenUtil;
import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.model.*;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.service.JwtUserDetailsService;
import com.ab0529.absite.service.RoleService;
import com.ab0529.absite.service.TokenBlacklistService;
import com.ab0529.absite.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Slf4j
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
	private TokenBlacklistService tokenBlacklistService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * LOGIN
	 * Handles login authentication
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		final ResponseEntity<?> ERR_USER_DISABLED = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: user is disabled").asResponseEntity();
		final ResponseEntity<?> ERR_BAD_CREDENTIALS = new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: bad login").asResponseEntity();

		try {
			log.info("POST /api/auth/login");
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			// Load user
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			// Generate JwtToken with ip claim
			String ipAndAgent = request.getRemoteAddr() + request.getHeader("User-Agent");
			final String token = jwtTokenUtil.generateTokenWithIPAndUserAgentClaim(userDetails, ipAndAgent);
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
			log.info("POST /api/auth/register");
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

	/*
	* LOGOUT
	* Handles user logout
	*/
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/logout/{token}")
	public ResponseEntity<?> authLogout(@PathVariable String token, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		// Make sure token belongs to logged in user
		String name = jwtTokenUtil.getUsernameFromToken(token);
		if (!customUserDetails.getUsername().equals(name))
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "error: unauthorized logout").asResponseEntity();

		// Add token to blacklist
		TokenBlacklist tokenBlacklist = new TokenBlacklist(token);
		tokenBlacklistService.save(tokenBlacklist);

		return new ApiResponse(HttpStatus.OK, "logout successful", tokenBlacklist).asResponseEntity();
	}
}
