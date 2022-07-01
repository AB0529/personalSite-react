package com.ab0529.absite.service;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
}
