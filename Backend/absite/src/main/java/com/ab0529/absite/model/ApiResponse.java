package com.ab0529.absite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Data
@Getter
@Setter
public class ApiResponse {
	private HttpStatus status;
	private int code;
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
		return new ResponseEntity<>(new ApiResponse(status, status.value(), message, result), status);
	}
}
