package com.ab0529.absite.model;

public enum EAuthority {
	// Allows for deletion of other users
	USER_DELETE,
	// Allows for editing of other users
	USER_EDIT,
	// Allows for editing of other user's roles only (except ROLE_ADMIN)
	USER_ROLE_EDIT,
	// Allows for editing of other user's authorities only (except ADMIN_ROLE_ADD and ADMIN_ROLE_REMOVE)
	USER_AUTHORITY_EDIT,
	// Allows for adding roles to other users (except ROLE_ADMIN)
	USER_ROLE_ADD,
	// Allows for adding auth to other users
	USER_AUTHORITY_ADD,
	// Allows for removing roles from other users (except ROLE_ADMIN)
	USER_ROLE_REMOVE,
	// Allows for removing auth from other users
	USER_AUTHORITY_REMOVE,
	// Allows for adding ROLE_ADMIN to other users
	ADMIN_ROLE_ADD,
	// Allows for removing ROLE_ADMIN from other users
	ADMIN_ROLE_REMOVE,
	// Allows for deletion of other users file's
	FILE_REMOVE,
	// Allows for viewing of all users
	USER_VIEW,
	// Allows for viewing of all files
	FILE_VIEW
}
