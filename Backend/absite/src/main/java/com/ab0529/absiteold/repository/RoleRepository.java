package com.ab0529.absiteold.repository;

import com.ab0529.absiteold.entity.ERole;
import com.ab0529.absiteold.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}
