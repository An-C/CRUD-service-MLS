package com.mls.users.service;

import com.mls.users.model.User;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Service to deal with Users
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
public interface UserService extends BaseService<User> {

	/**
	 * Update entity
	 * </p>
	 * @param id     - user identifier
	 * @param fields - fields to update
	 * @return updated entity
	 * @throws Exception on fail
	 */
	User update(UUID id, JSONObject fields) throws Exception;

	/**
	 * Delete user
	 * actually, just mark as a hidden to have an opportunity to restore later
	 * </p>
	 * @param id - user identifier
	 * @return updated entity
	 * @throws Exception on fail
	 */
	User delete(UUID id) throws Exception;
}
