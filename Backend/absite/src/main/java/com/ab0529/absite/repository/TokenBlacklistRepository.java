package com.ab0529.absite.repository;

import com.ab0529.absite.entity.TokenBlacklist;
import com.ab0529.absite.model.EBlacklistReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
	Boolean existsByToken(String jwtToken);
}
