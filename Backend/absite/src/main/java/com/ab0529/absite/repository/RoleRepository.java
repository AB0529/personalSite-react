package com.ab0529.absite.repository;

import com.ab0529.absite.model.ERole;
import com.ab0529.absite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
}
