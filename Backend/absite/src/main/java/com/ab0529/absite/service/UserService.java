package com.ab0529.absite.service;

import com.ab0529.absite.entity.User;

public interface UserService {
	Iterable<User> getAllUsersLimited(int max);
}
