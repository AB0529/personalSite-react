package com.ab0529.absite.service;

import com.ab0529.absite.entity.ERole;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.repository.RoleRepository;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
	@Autowired
	RoleRepository roleRepository;

	public Optional<Role> findByName(ERole name) {
		return roleRepository.findByName(name);
	}

	public Optional<Role> findById(Long roleId) {
		return roleRepository.findById(roleId);
	}
}
