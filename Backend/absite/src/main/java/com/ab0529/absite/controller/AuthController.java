package com.ab0529.absite.controller;

import com.ab0529.absite.config.jwt.JwtUtils;
import com.ab0529.absite.entity.ERole;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.ApiResponse;
import com.ab0529.absite.model.JwtResponseModel;
import com.ab0529.absite.model.LoginModel;
import com.ab0529.absite.model.UserModel;
import com.ab0529.absite.repository.RoleRepository;
import com.ab0529.absite.repository.UserRepository;
import com.ab0529.absite.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginModel loginModel, HttpServletRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Map<String, Object> claims = new HashMap<>();

		claims.put("ip", request.getRemoteAddr());

		String jwt = jwtUtils.generateJwtTokenWithClaims(authentication, claims);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return new ApiResponse(HttpStatus.OK, "Authentication successful!", new JwtResponseModel(
						userDetails.getUsername(),
						userDetails.getEmail(),
						userDetails.getLastName(),
						userDetails.getLastName(),
						roles,
						jwt
				)).responseEntity();
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserModel userModel) {
		// Make sure username is not taken
		if (userRepository.existsByUsername(userModel.getUsername()))
			return new ApiResponse(HttpStatus.BAD_REQUEST, "Error: Username is already taken!", null).responseEntity();
		// Make sure email is not taken
		if (userRepository.existsByEmail(userModel.getEmail()))
			return new ApiResponse(HttpStatus.BAD_REQUEST, "Error: Email is already in use!", null).responseEntity();

		// Create the new account
		User user = new User(
				userModel.getUsername(),
				bcryptEncoder.encode(userModel.getPassword()),
				userModel.getFirstName(),
				userModel.getLastName(),
				userModel.getEmail());

		// Assign roles
		Role userRoll = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role not found!"));
		user.addRole(userRoll);

		return new ApiResponse(HttpStatus.OK, "User registered successfully!", userRepository.save(user)).responseEntity();
	}

//	@PostMapping("/logout")
//	public ResponseEntity<?> logout() {
//		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new ApiResponse(HttpStatus.OK, "You've been signed out!", null));
//	}
}
