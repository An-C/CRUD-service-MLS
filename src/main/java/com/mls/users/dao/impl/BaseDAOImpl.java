package com.mls.users.dao.impl;

import com.mls.users.dao.BaseDAO;
import com.mls.users.exception.DataBaseException;
import com.mls.users.tools.ExceptionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Implementation of {@link com.mls.users.dao.BaseDAO} interface
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
@Component
public abstract class BaseDAOImpl<T, PK extends Serializable> implements BaseDAO<T, PK> {

	/**
	 * Entity Manager
	 */
	@PersistenceContext
	EntityManager entityManager;


	/**
	 * Tool for exceptions handling
	 */
	@Autowired
	ExceptionTools exceptionTools;


	/**
	 * Entity class
	 */
	Class<T> entityClass;


	/**
	 * Class constructor
	 */
	protected BaseDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}


	/**
	 * @see com.mls.users.dao.BaseDAO#get()
	 */
	@Override
	public List<T> get() throws DataBaseException {
		return null;
	}


	/**
	 * @see com.mls.users.dao.BaseDAO#get(Serializable)
	 */
	@Override
	public T get(PK id) throws NoResultException, DataBaseException {
		try {
			T entity = entityManager.find(entityClass, id);
			if (entity == null) {
				throw new NoResultException("Cannot get entity of class " + entityClass + " with id " + id);
			}
			return entity;
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Error getting entity ", null, new Object[]{id}, e);
		}
	}

	/**
	 * @see com.mls.users.dao.BaseDAO#create(Object)
	 */
	@Override
	public T create(T entity) throws DataBaseException {
		try {
			entityManager.persist(entity);
			return entity;
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot create entity of class " + entity.getClass(), null, new Object[]{}, e);
		}
	}

	/**
	 * @see com.mls.users.dao.BaseDAO#update(Object)
	 */
	@Override
	public T update(T entity) throws DataBaseException {
		try {
			return entityManager.merge(entity);
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot update entity of class " + entity.getClass(), null, new Object[]{}, e);
	}

	}


	/**
	 * @see com.mls.users.dao.BaseDAO#delete(Object)
	 */
	@Override
	public void delete(T entity) throws DataBaseException {
		try {
			T mergedEntity = entityManager.merge(entity);
			this.entityManager.remove(mergedEntity);
		} catch (Exception e) {
			throw exceptionTools.dataBaseException("Cannot delete entity of class " + entity.getClass(), null, new Object[]{}, e);
		}
	}
}
