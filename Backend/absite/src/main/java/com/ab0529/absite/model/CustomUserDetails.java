package com.ab0529.absite.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails extends User {
	private final long id;

	public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
	                         boolean credentialsNonExpired,
	                         boolean accountNonLocked,
	                         Collection<? extends GrantedAuthority> authorities, int userID)
	{
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = userID;
	}

	public CustomUserDetails(String username, String password, List<SimpleGrantedAuthority> authorities, long id) {
		super(username, password, true, true, true, true, authorities);
		this.id = id;
	}
}
