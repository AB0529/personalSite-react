package com.ab0529.absite.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {
	@NonNull
	private HttpStatus status;
	private int code;
	@NonNull
	private String message;

	private Object result;

	public ApiResponse(HttpStatus status, String message, Object result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}

	public ApiResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public ResponseEntity<?> asResponseEntity() {
		return new ResponseEntity<>(new ApiResponse(status, code, message, result), status);
	}
}
