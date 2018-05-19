package com.mls.users.exception;

import org.springframework.dao.DataAccessException;

/**
 * Exceptions from the database
 * <p/>
 * @author Anastasya Chizhikova
 * @since 17.05.2018
 */
public class DataBaseException extends DataAccessException {

	/**
	 * Serialization UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with parameters
	 * </p>
	 * @param message exception message
	 */
	public DataBaseException(String message) {
		super(message);
	}

	/**
	 * Constructor with parameters
	 * </p>
	 * @param message - exception message
	 * @param cause   - exception cause
	 */
	public DataBaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
