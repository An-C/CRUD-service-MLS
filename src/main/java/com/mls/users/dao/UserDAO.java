package com.mls.users.dao;

import com.mls.users.exception.DataBaseException;
import com.mls.users.model.User;

import javax.persistence.NoResultException;
import java.util.UUID;

/**
 * User DAO
 * <p/>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
public interface UserDAO {


	/**
	 * Hide user by identifier
	 * <p/>
	 * @param id user identifier
	 * @return user entity
	 * @throws NoResultException if no entity found by provided identifier
	 * @throws DataBaseException on other database fails
	 */
	User delete(UUID id) throws NoResultException, DataBaseException;


}
