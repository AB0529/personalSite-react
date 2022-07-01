package com.ab0529.absite.entity;

public enum ERole {
	// Standard user
	ROLE_USER,
	// Superuser can do anything
	ROLE_ADMIN,
	// Allows for deletion of other users
	USER_DELETE_AUTHORITY,
	// Allows for editing of other users
	USER_EDIT_AUTHORITY,
	// Allows for adding roles to other users (except ROLE_ADMIN)
	USER_ROLE_ADD_AUTHORITY,
	// Allows for adding auth to other users
	USER_AUTH_ADD_AUTHORITY,
	// Allows for removing roles from other users (except ROLE_ADMIN)
	USER_ROLE_REMOVE_AUTHORITY,
	// Allows for removing auth from other users
	USER_AUTH_REMOVE_AUTHORITY,
	// Allows for adding ROLE_ADMIN to other users
	ADMIN_ROLE_ADD_AUTHORITY,
	// Allows for removing ROLE_ADMIN from other users
	ADMIN_ROLE_REMOVE_AUTHORITY,
	// Allows for deletion of other users file's
	USER_FILE_REMOVE_AUTHORITY
}
