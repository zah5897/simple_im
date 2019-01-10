package com.mobile.im.exception;

public class AppException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ERROR error;
	private RuntimeException realException;

	public AppException(ERROR error) {
		this.error = error;
	}

	public AppException(ERROR error, RuntimeException realException) {
		this.error = error;
		this.realException = realException;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ERROR getError() {
		return error;
	}

	public RuntimeException getRealException() {
		return realException;
	}

}
