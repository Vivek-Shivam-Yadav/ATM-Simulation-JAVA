package com.atm.exception;

public class InvalidAmountException extends ATMException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAmountException(String message) {
        super(message);
    }
}