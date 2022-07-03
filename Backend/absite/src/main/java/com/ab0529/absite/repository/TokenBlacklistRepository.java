package com.ab0529.absite.repository;

import com.ab0529.absite.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
	Boolean existsByToken(String jwtToken);
}
