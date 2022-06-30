package com.ab0529.absite.repository;

import com.ab0529.absite.entity.File;
import com.ab0529.absite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, UUID> {
	Iterable<File> findByName(String name);
	Iterable<File> findByNameAndUserId(String name, Long id);
	@Query(value = "SELECT CAST(id as varchar), name, user_id, content_type, content FROM files LIMIT :m", nativeQuery = true)
	Iterable<Object> findAllLimited(@Param("m") Integer max);

	Optional<File> findByIdAndUserId(UUID fileId, Long userId);

	@Query(value = "SELECT CAST(id as varchar), name, user_id, content_type, content FROM files WHERE user_id = :uid LIMIT :m", nativeQuery = true)
	Iterable<Object> findAllByIdLimited(@Param("uid") long id, @Param("m") int max);
}
