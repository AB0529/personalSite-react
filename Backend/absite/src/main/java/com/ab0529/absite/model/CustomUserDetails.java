package com.ab0529.absite.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Data
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
}
