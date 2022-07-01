package com.ab0529.absiteold.service;

import com.ab0529.absiteold.entity.User;
import com.ab0529.absiteold.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl {
	@Autowired
	UserRepository userRepository;

	@Transactional
	public Iterable<User> getAllUsersLimited(int max) {
		return userRepository.findAllLimited(max);
	}
}
