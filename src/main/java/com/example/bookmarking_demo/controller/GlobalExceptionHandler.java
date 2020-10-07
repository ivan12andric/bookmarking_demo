package com.example.bookmarking_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.bookmarking_demo.model.ExceptionResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice(assignableTypes = { KorisnikController.class, BookmarkController.class })
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {

		log.error("Handling '{}' due to '{}'", ex.getClass().getSimpleName(), ex.getMessage());

		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.kodGreske(500)
				.opisGreske(ex.getMessage())
				.build();

		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
