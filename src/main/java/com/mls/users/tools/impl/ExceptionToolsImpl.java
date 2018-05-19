package com.mls.users.tools.impl;

import com.mls.users.exception.DataBaseException;
import com.mls.users.tools.ExceptionTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link com.mls.users.tools.ExceptionTools} interface
 * </p>
 * @author Anastasya Chizhikova
 * @since 18.05.2018
 */
public class ExceptionToolsImpl implements ExceptionTools{

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(ExceptionToolsImpl.class);

	/**
	 * @see com.mls.users.tools.ExceptionTools#dataBaseException(String, String, Object[], Throwable)
	 */
	@Override
	public DataBaseException dataBaseException(String message, String queryString, Object[] queryParameters, Throwable e) throws DataBaseException {
		String output = null;
		try {
			StringBuilder exceptionMessage = new StringBuilder();

			// Developer message
			if (message != null)
				if (!message.isEmpty())
					exceptionMessage
							.append(message)
							.append("\n\n");

			// Query
			if (queryString != null && !queryString.isEmpty())
				exceptionMessage
						.append("Query:\n ")
						.append(queryString)
						.append("\n\n");


			// Query parameters
			if (queryParameters != null && queryParameters.length > 0) {
				exceptionMessage.append("Query parameters:\n");
				for (int i = 0; i < queryParameters.length; i++) {
					exceptionMessage
							.append(" Parameter ")
							.append(i + 1)
							.append(" = ")
							.append(queryParameters[i])
							.append("\n");
				}
			}

			output = exceptionMessage.toString();
			if (output.isEmpty())
				output = "Exception information is not provided)";
		} catch (Exception e1) {
			logger.warn("Failed to build exception information", e1);
		}

		if (e != null)
			return new DataBaseException(output, e);
		else
			return new DataBaseException(output);
	}


	/**
	 * @see com.mls.users.tools.ExceptionTools#runtimeException(String, Exception, Logger)
	 */
	@Override
	public RuntimeException runtimeException (String message, Exception e, Logger logger) {
		if (e != null) {
			logger.error(message, e);
			return new RuntimeException(message, e);
		} else {
			logger.error(message);
			return new RuntimeException(message);
		}
	}
}
