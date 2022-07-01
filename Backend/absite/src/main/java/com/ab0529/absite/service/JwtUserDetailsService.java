package com.ab0529.absite.service;

import com.ab0529.absite.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		// User's authorities
		List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).toList();

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
}
