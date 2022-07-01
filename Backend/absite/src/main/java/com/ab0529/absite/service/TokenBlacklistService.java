package com.ab0529.absite.service;

import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.model.EBlacklistReason;
import com.ab0529.absite.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
