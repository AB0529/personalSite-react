package com.ab0529.absite.service;

import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
	@Autowired
	TokenBlacklistRepository blacklistRepository;

	public Boolean existsByToken(String jwtToken) {
		return blacklistRepository.existsByToken(jwtToken);
	}

	public void save(TokenBlacklist tokenBlacklist) {
		blacklistRepository.save(tokenBlacklist);
	}

	public void deleteAll() {
		blacklistRepository.deleteAll();
	}
}
