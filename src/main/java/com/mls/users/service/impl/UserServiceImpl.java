package com.mls.users.service.impl;

import com.mls.users.dao.impl.UserDAOImpl;
import com.mls.users.model.User;
import com.mls.users.service.UserService;
import com.mls.users.tools.ExceptionTools;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for {@link UserService} interface
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	/**
	 * Logging
	 */
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * DAO to deal with user entity
	 */
	@Autowired
	private UserDAOImpl userDAO;

	/**
	 * Tool for exceptions handling
	 */
	@Autowired
	ExceptionTools exceptionTools;


	/**
	 * @see UserService#get()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> get() throws Exception {
		try {
			return userDAO.get();
		} catch (NoResultException ex){
			throw ex;
		}catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to get users list.", e, logger);
		}
	}


	/**
	 * @see UserService#get(UUID)
	 */
	@Override
	public User get(UUID id) throws Exception {
		try {
			return userDAO.get(id);
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to get user by id: " + id, e, logger);
		}
	}


	/**
	 * @see com.mls.users.service.BaseService#create(java.io.Serializable)
	 */
	@Override
	public User create(User user) throws Exception {
		try {
			// validate email
			// here may be another fields validation as well
			if (!StringUtils.isEmpty(user.getEmail()))
				validateEmail(user.getEmail(), user.getId());

			// hide user
			user.setHidden(Boolean.FALSE);

			return userDAO.create(user);
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to create a new user. User details: " + user.toString(), e, logger);
		}
	}

	/**
	 * @see com.mls.users.service.BaseService#update(java.io.Serializable)
	 */
	@Override
	public User update(User user) throws Exception {
		logger.trace("Method UserServiceImpl.update() has started");
		try {
			// validate email
			// here may be another fields validation as well
			if (!StringUtils.isEmpty(user.getEmail()))
				validateEmail(user.getEmail(), user.getId());

			return userDAO.update(user);
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to update user. User details: " + user.toString(), e, logger);
		}
	}


	/**
	 * @see com.mls.users.service.UserService#update(UUID, org.json.JSONObject)
	 */
	@Override
	public User update(UUID id, JSONObject fields) throws Exception {
		logger.trace("Method UserServiceImpl.update(id, fields) has started with"
						+ "\nid: "+id
						+ "\nfields:" + String.valueOf(fields) );
		try {
			User userToUpdate = get(id);

			if (fields.has("userName"))
				userToUpdate.setUserName(String.valueOf(fields.get("userName")));
			if (fields.has("password"))
				userToUpdate.setPassword(String.valueOf(fields.get("password")));
			if (fields.has("address"))
				userToUpdate.setAddress(String.valueOf(fields.get("address")));
			if (fields.has("email")) {
				String email = String.valueOf(fields.get("email"));
				validateEmail(email, id);
				userToUpdate.setEmail(email);
			}
			if (fields.has("phone")) {
				//here also may be a phone validation if necessary
				userToUpdate.setPhone(String.valueOf(fields.get("phone")));
			}

			return userDAO.update(userToUpdate);
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to update user. \nUser id: " + id + "\nfields:" + String.valueOf(fields), e, logger);
		}
	}


	/**
	 * @see com.mls.users.service.BaseService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(User user) throws Exception {
		logger.trace("Method UserServiceImpl.delete(user) has started with user:"+ user.toString());
		try {
			userDAO.delete(user);
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to disable user. User details: " + user.toString(), e, logger);
		}
	}

	/**
	 * @see com.mls.users.service.UserService#delete(UUID)
	 */
	@Override
	public User delete(UUID id) throws Exception {
		logger.trace("Method UserServiceImpl.delete(id) has started with id:"+ id);
		try {
			return userDAO.delete(id);
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.runtimeException("Failed to delete(hide) user. User id: " + id, e, logger);
		}
	}

	/**
	 * Check email validity
	 * </p>
	 * @param email  - email to check
	 * @param userId - user identifier for warning message
	 */
	private void validateEmail(String email, UUID userId) {
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException ex) {
			//behavior may differs depending on business logic
			logger.warn("Incorrect email " + email + " is adjusted for user " + userId);
		}
	}

}
