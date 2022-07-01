package com.ab0529.absiteold.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ApiResponse {
	private HttpStatus status;
	private String message;
	private Object result;


	public ResponseEntity<?> responseEntity() {
		return new ResponseEntity<>(new ApiResponse(status, message, result), status);
	}
}
