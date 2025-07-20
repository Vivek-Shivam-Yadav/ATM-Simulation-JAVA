package com.atm.exception;

public class AccountBlockedException extends ATMException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountBlockedException(String message) {
        super(message);
    }
}