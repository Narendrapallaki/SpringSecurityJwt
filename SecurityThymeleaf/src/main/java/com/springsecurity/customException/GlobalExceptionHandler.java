package com.springsecurity.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserIdNotFound.class)
	public ResponseEntity<com.springsecurity.Entity.ResponseStatus> result(UserIdNotFound pif) {

		com.springsecurity.Entity.ResponseStatus status = new com.springsecurity.Entity.ResponseStatus();

		status.setResponse(pif.getMessage());
		status.setStatus(String.valueOf(HttpStatus.NOT_FOUND));

		return ResponseEntity.ok(status);
	}

}
