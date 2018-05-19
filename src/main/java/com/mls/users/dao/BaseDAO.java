package com.mls.users.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Common DAO interface to deal with entities in standard manner
 * <p/>
 * @author Anastasya Chizhikova
 * @since 16.05.208
 */
public interface BaseDAO<T, PK extends Serializable> {

	/**
	 * Get entity list
	 * <p/>
	 * @return entity list
	 * @throws Exception on fails
	 */
	List<T> get() throws Exception;


	/**
	 * Get User entity by identifier
	 * <p/>
	 * @param id entity identifier
	 * @return entity model
	 * @throws Exception on fails
	 */
	T get(PK id) throws Exception;

	/**
	 * Create a new entity
	 * <p/>
	 * @param entity entity
	 * @return entity
	 * @throws Exception on fails
	 */
	T create(T entity) throws Exception;

	/**
	 * Edit entity
	 * <p/>
	 * @param entity entity
	 * @return entity
	 * @throws Exception on fails
	 */
	T update(T entity) throws Exception;


	/**
	 * Delete entity
	 * <p/>
	 * @param entity entity
	 * @throws Exception on fails
	 */
	void delete(T entity) throws Exception;
}
