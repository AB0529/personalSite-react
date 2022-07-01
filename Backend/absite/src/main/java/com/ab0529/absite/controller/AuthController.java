package com.ab0529.absite.controller;

import com.ab0529.absite.component.JwtTokenUtil;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.LoginRequest;
import com.ab0529.absite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Auth route controller
 * Routes: /api/auth/login, /api/auth/register
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserService userService;

	/**
	 * LOGIN
	 * Handles login authentication
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			// Load user
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			// Generate JwtToken with ip claim
			final String token = jwtTokenUtil.generateTokenWithRemoteAddrClaim(userDetails, request.getRemoteAddr());
			// Return token if authentication was successful
			return new ApiResponse(HttpStatus.OK, "authentication successful", token).asResponseEntity();
		} catch (DisabledException e) {
			return new ApiResponse(HttpStatus.UNPROCESSABLE_ENTITY, "error: user is disabled").asResponseEntity();
		} catch (BadCredentialsException e) {
			return new ApiResponse(HttpStatus.FORBIDDEN, "error: bad credentials").asResponseEntity();
		}
	}
}
