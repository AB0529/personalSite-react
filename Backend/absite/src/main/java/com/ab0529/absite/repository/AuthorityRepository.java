package com.ab0529.absite.repository;

import com.ab0529.absite.entity.Authority;
import com.ab0529.absite.entity.Role;
import com.ab0529.absite.model.EAuthority;
import com.ab0529.absite.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByName(EAuthority name);
}
