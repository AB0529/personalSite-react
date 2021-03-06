package com.ab0529.absite.service;

import com.ab0529.absite.entity.Role;
import com.ab0529.absite.entity.User;
import com.ab0529.absite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EntityManagerFactory entityManagerFactory;

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	public void save(User user) {
		userRepository.save(user);
	}
	public void delete(User user) {
		userRepository.delete(user);
	}

	public Page<User> findAllLimit(int max) {
		return userRepository.findAll(PageRequest.of(0, max));
	}

	public void addRoleToUser(Long userid, Long roleId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT INTO users_roles(user_id, role_id) VALUES(?, ?)")
				.setParameter(1, userid)
				.setParameter(2, roleId)
				.executeUpdate();
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	public void removeRoleFromUser(Long userid, Long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("DELETE FROM users_roles WHERE role_id=? AND user_id=?")
				.setParameter(1, id)
				.setParameter(2, userid)
				.executeUpdate();
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	public void addAuthorityToUser(Long userid, long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT INTO users_authorities(user_id, authorities_id) VALUES(?, ?)")
				.setParameter(1, userid)
				.setParameter(2, id)
				.executeUpdate();
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	public void removeAuthorityFromUser(Long userid, long id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("DELETE FROM users_authorities WHERE authorities_id=? AND user_id=?")
				.setParameter(1, id)
				.setParameter(2, userid)
				.executeUpdate();
		entityManager.flush();
		entityManager.getTransaction().commit();
	}
}
