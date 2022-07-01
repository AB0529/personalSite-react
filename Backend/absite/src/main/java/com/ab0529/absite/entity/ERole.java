package com.ab0529.absite.entity;

public enum ERole {
	// Standard user
	ROLE_USER,
	// Superuser can do anything
	ROLE_ADMIN,
	// Allows for deletion of other users
	ROLE_USER_DELETE,
	// Allows for editing of other users
	ROLE_USER_EDIT,
	// Allows for adding roles to other users (except ROLE_ADMIN)
	ROLE_USER_ROLE_ADD,
	// Allows for removing roles from other users (except ROLE_ADMIN)
	ROLE_USER_ROLE_REMOVE,
	// Allows for adding ROLE_ADMIN to other users
	ROLE_ADMIN_ROLE_ADD,
	// Allows for removing ROLE_ADMIN from other users
	ROLE_ADMIN_ROLE_REMOVE,
	// Allows for deletion of other users file's
	ROLE_USER_FILE_REMOVE
}
