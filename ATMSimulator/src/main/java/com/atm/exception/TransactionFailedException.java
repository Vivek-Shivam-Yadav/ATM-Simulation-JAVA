package com.atm.exception;

public class TransactionFailedException extends ATMException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionFailedException(String message) {
        super(message);
    }
}