package com.daksu.security.filter.exception;

import org.springframework.security.core.AuthenticationException;

public class CompanyDoesNotExistException extends AuthenticationException {

	public CompanyDoesNotExistException(String msg) {
		super(msg);
	}

}
