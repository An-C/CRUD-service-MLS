package com.mls.users.service;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Common service to deal with entities
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
public interface BaseService<T extends Serializable> {

	/**
	 * Get list of entities
	 * @return list of entities
	 * @throws Exception on fail
	 */
	List<T> get() throws Exception;


	/**
	 * Get entity by identifier
	 * @param id entity identifier
	 * @return entity
	 * @throws IllegalArgumentException on fail
	 */
	T get(UUID id) throws Exception;

	/**
	 * Create a new entity
	 * @param entity entity to save
	 * @return saved entity
	 * @throws Exception on fail
	 */
	T create(T entity) throws Exception;

	/**
	 * Update entity
	 * @param entity entity with modifications
	 * @return updated entity
	 * @throws Exception on fail
	 */
	T update(T entity) throws Exception;

	/**
	 * Delete entity
	 * @param entity entity to delete
	 * @throws Exception on fail
	 */
	void delete(T entity) throws Exception;

}
