package com.ab0529.absiteold.repository;

import com.ab0529.absiteold.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	@Query(value = "SELECT * FROM users LIMIT :m", nativeQuery = true)
	Iterable<User> findAllLimited(@Param("m") int max);
}
