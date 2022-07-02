package com.ab0529.absite.model;

public enum EAuthority {
	// Allows for deletion of other users
	USER_DELETE,
	// Allows for editing of other users
	USER_EDIT,
	// Allows for adding roles to other users (except ROLE_ADMIN)
	USER_ROLE_ADD,
	// Allows for adding auth to other users
	USER_AUTH_ADD,
	// Allows for removing roles from other users (except ROLE_ADMIN)
	USER_ROLE_REMOVE,
	// Allows for removing auth from other users
	USER_AUTH_REMOVE,
	// Allows for adding ROLE_ADMIN to other users
	ADMIN_ROLE_ADD,
	// Allows for removing ROLE_ADMIN from other users
	ADMIN_ROLE_REMOVE,
	// Allows for deletion of other users file's
	USER_FILE_REMOVE,
	// Allows for banning of users
	USER_BAN_ADD,
	// Allows for unbanning of users
	USER_BAN_REMOVE,
	// Allows for full control of banned users
	USER_BAN,
	// Allows for viewing banned users
	USER_BAN_VIEW,
}
