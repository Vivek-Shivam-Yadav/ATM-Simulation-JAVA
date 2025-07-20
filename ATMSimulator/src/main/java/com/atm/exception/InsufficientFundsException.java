package com.atm.exception;

public class InsufficientFundsException extends ATMException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
        super(message);
    }
}