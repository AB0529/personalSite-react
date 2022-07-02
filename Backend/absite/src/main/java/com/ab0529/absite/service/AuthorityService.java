package com.ab0529.absite.service;

import com.ab0529.absite.entity.Authority;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.model.EAuthority;
import com.ab0529.absite.model.ERole;
import com.ab0529.absite.repository.AuthorityRepository;
import com.ab0529.absite.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {
	@Autowired
	AuthorityRepository authorityRepository;

	public Optional<Authority> findByName(EAuthority name) {
		return authorityRepository.findByName(name);
	}

	public Optional<Authority> findById(Long roleId) {
		return authorityRepository.findById(roleId);
	}
}
