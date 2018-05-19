package com.mls.users.dao.impl;


import com.mls.users.dao.UserDAO;
import com.mls.users.exception.DataBaseException;
import com.mls.users.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;


/**
 * Implementation of {@link com.mls.users.dao.UserDAO} interface
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
@Component
public class UserDAOImpl extends BaseDAOImpl<User, UUID> implements UserDAO {

	/**
	 * Get un-hidden users entity list
	 * <p/>
	 * @see com.mls.users.dao.BaseDAO#get()
	 */
	@Override
	public List<User> get() throws DataBaseException {
		String query = "SELECT u from User u where u.hidden = false order by u.userName";
		try {
			TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
			return typedQuery.getResultList();
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot get user list", query, new Object[]{}, e);
		}
	}


	/**
	 * Get User entity by identifier if user is not hidden
	 * <p/>
	 * @see com.mls.users.dao.BaseDAO#get(java.io.Serializable)
	 */
	@Override
	public User get(UUID id) throws NoResultException, DataBaseException {
		String query = "SELECT u from User u where u.hidden = false and u.id= :id";
		try {
			Query resultQuery = entityManager.createQuery(query).setParameter("id", id);
			return (User) resultQuery.getSingleResult();
		} catch (NoResultException ex){
			throw ex;
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot get User by id", query, new Object[]{id}, e);
		}
	}


	/**
	 * Disable user
	 * <p/>
	 * @see com.mls.users.dao.UserDAO#delete(UUID)
	 */
	@Override
	public User delete(UUID id) throws NoResultException, DataBaseException {
		try {
			User user = get(id);
			user.setHidden(Boolean.TRUE);
			return update(user);
		} catch (NoResultException ex){
			throw ex;
		}catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot hide/disable User by id", null, new Object[]{id}, e);
		}
	}
}
