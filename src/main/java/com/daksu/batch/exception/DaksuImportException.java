package com.daksu.batch.exception;

public class DaksuImportException extends Exception {

	public DaksuImportException(String message) {
		super(message);
	}
	
	public DaksuImportException(Exception e) {
		super(e);
	}

}
