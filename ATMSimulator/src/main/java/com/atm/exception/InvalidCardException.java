package com.atm.exception;

public class InvalidCardException extends ATMException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCardException(String message) {
        super(message);
    }
}