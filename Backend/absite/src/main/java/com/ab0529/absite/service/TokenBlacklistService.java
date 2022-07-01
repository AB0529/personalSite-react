package com.ab0529.absite.service;

import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.model.EBlacklistReason;
import com.ab0529.absite.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenBlacklistService {
	@Autowired
	TokenBlacklistRepository blacklistRepository;

	public Optional<TokenBlacklist> findById(Long id) {
		return blacklistRepository.findById(id);
	}

	public Optional<TokenBlacklist> findByToken(String token) {
		return blacklistRepository.findByToken(token);
	}
	public Optional<TokenBlacklist> findByReason(EBlacklistReason reason) {
		return blacklistRepository.findByReason(reason);
	}

	public void save(TokenBlacklist tokenBlacklist) {
		blacklistRepository.save(tokenBlacklist);
	}
}
