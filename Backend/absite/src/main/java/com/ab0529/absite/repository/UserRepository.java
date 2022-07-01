package com.ab0529.absite.repository;

import com.ab0529.absite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UUID, User> {
	Optional<User> findByUsername(String username);
}
