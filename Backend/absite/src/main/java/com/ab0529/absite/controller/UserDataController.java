package com.ab0529.absite.controller;

import com.ab0529.absite.config.jwt.JwtUtils;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.model.*;
import com.ab0529.absite.repository.UserRepository;
import com.ab0529.absite.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserDataController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder bcryptEncoder;

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
	@DeleteMapping("delete/{id}")
	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(Authentication authentication, @PathVariable Long id, HttpServletRequest request ) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Optional<User> dbUser = userRepository.findByUsername(userDetails.getUsername());

		// Make sure file exists
		if (dbUser.isEmpty())
			return new ApiResponse(HttpStatus.NOT_FOUND, "User not found", null).responseEntity();
		// Make sure user is owner the file or is an admin
		if (!request.isUserInRole("ROLE_ADMIN") && dbUser.get().getId() != id)
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized access", null).responseEntity();

		// Delete the user
		userRepository.deleteById(id);

		return new ApiResponse(HttpStatus.OK, "User deleted", null).responseEntity();
	}

	@PostMapping("/admin/update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminUpdate(@RequestBody UserUpdateModel userUpdateModel) {
		User dbuser = userRepository.findByUsername(userUpdateModel.getOldUsername()).orElseThrow();
		// Update user
		dbuser.setUsername(userUpdateModel.getUsername());
		dbuser.setEmail(userUpdateModel.getEmail());
		dbuser.setFirstName(userUpdateModel.getFirstName());
		dbuser.setLastName(userUpdateModel.getLastName());

		System.out.println(userUpdateModel.getPassword());
		if (!userUpdateModel.getPassword().isEmpty())
			dbuser.setPassword(bcryptEncoder.encode(userUpdateModel.getPassword()));

		userRepository.save(dbuser);

		return new ApiResponse(HttpStatus.OK, "User has been updated", null).responseEntity();
	}

	@PostMapping("/update")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
	public ResponseEntity<?> userUpdate(@RequestBody UserUpdateModel userUpdateModel) {
		User dbuser = userRepository.findByUsername(userUpdateModel.getOldUsername()).orElseThrow();
		// Make sure user is editing own user
		if (!dbuser.getUsername().equals(userUpdateModel.getOldUsername()))
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized update", null).responseEntity();
		// Update user

		// Make sure username is not taken
		if (!userUpdateModel.getOldUsername().equals(userUpdateModel.getUsername()) && userRepository.existsByUsername(userUpdateModel.getUsername()))
			return new ApiResponse(HttpStatus.BAD_REQUEST, "Username is already in use", null).responseEntity();
		// Make sure email is not taken
		if (!userUpdateModel.getOldEmail().equals(userUpdateModel.getEmail()) && userRepository.existsByEmail(userUpdateModel.getEmail()))
			return new ApiResponse(HttpStatus.BAD_REQUEST, "Email is already in use", null).responseEntity();
		dbuser.setUsername(userUpdateModel.getUsername());
		dbuser.setEmail(userUpdateModel.getEmail());
		dbuser.setFirstName(userUpdateModel.getFirstName());
		dbuser.setLastName(userUpdateModel.getLastName());

		System.out.println(userUpdateModel.getPassword());
		if (!userUpdateModel.getPassword().isEmpty())
			dbuser.setPassword(bcryptEncoder.encode(userUpdateModel.getPassword()));

		userRepository.save(dbuser);

		return new ApiResponse(HttpStatus.OK, "User has been updated", null).responseEntity();
	}

}
