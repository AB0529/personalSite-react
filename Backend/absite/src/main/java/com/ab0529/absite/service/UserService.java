package com.ab0529.absite.service;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EntityManager entityManager;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	public void save(User user) {
		userRepository.save(user);
	}
	public void delete(User user) {
		userRepository.delete(user);
	}

	public Page<User> findAllLimit(int max) {
		return userRepository.findAll(PageRequest.of(0, max));
	}
}
