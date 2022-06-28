package com.ab0529.absite.service;

import com.ab0529.absite.entity.User;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public Iterable<User> getAllUsersLimited(int max) {
		return userRepository.findAllLimited(max);
	}
}
