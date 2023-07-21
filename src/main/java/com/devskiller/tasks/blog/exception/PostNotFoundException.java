package com.devskiller.tasks.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends IllegalArgumentException {
	public PostNotFoundException(final String message) {
		super(message);
	}
}
