package com.mls.users.tools;

import com.mls.users.exception.DataBaseException;
import org.slf4j.Logger;

/**
 * Interface to handle exceptions
 * </p>
 * @author Anastasya Chizhikova
 * @since 18.05.2018
 */
public interface ExceptionTools {

	/**
	 * Build exception information
	 * </p>
	 * @param message - developer message
	 * @param queryString - string of query
	 * @param queryParameters - parameters of query
	 * @param e - exception occurred
	 * @return formed database exception
	 * @throws DataBaseException exception fro database
	 */
	DataBaseException dataBaseException(String message, String queryString, Object[] queryParameters, Throwable e) throws DataBaseException;

	/**
	 * Log and throw RuntimeException
	 *</p>
	 * @param message Human message attached to the exception
	 * @param e Exception to throw; it can be null
	 * @param logger Logger object from the throwing class
	 */
	RuntimeException runtimeException (String message, Exception e, Logger logger) throws Exception;
}
