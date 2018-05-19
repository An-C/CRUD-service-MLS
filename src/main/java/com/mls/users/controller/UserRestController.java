package com.mls.users.controller;

import com.mls.users.model.User;
import com.mls.users.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * User actions controller
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */
@RestController
@CrossOrigin
public class UserRestController {

	/**
	 * Service to deal with users
	 */
	@Autowired
	public UserService userService;

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(UserRestController.class);


	/**
	 * API documentation
	 */
	@PostMapping(value = "v1")
	@SuppressWarnings("unchecked")
	public String getInfo() throws IOException {
		return "forward:/swagger/index.html";
	}

	/**
	 * Create a new User
	 * <p>
	 * @param user - new user data
	 * @return - new user entity as json/http status
	 */
	@PostMapping(value = "v1/users")
	@SuppressWarnings("unchecked")
	public ResponseEntity createUser(@RequestBody User user) {
		try {
			User newUser = userService.create(user);
			return new ResponseEntity(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return unsuccessful(HttpStatus.INTERNAL_SERVER_ERROR, "User creation failed, user:\n" + user.toString(), e);
		}
	}


	/**
	 * Update user data
	 * <p>
	 * @param id - user identifier
	 * @param fields - fields and values to update in json format
	 * @return - user entity (if success), operation status
	 */
	@PutMapping("v1/users/{id}")
	@SuppressWarnings("unchecked")
	public ResponseEntity updateUser(@PathVariable UUID id, @RequestBody String fields) {
		try {
			User updatedUser = userService.update(id, new JSONObject(fields));
			return new ResponseEntity(updatedUser, HttpStatus.OK);
		} catch (NoResultException ex) {
			return unsuccessful(HttpStatus.NOT_FOUND, "User doesn't exist, id: " + id, ex);
		} catch (Exception e) {
			return unsuccessful(HttpStatus.INTERNAL_SERVER_ERROR, "User updating failed, user id: "+id, e);
		}
	}


	/**
	 * Delete user
	 * <p>
	 * (actually we just hide user to have a possibility to easy restore all user data later)
	 * @param id - user identifier
	 * @return - operation status
	 */
	@DeleteMapping("v1/users/{id}")
	@SuppressWarnings("unchecked")
	public ResponseEntity deleteUser(@PathVariable("id") UUID id) {
		try {
			userService.delete(id);
			return new ResponseEntity(HttpStatus.OK);
		} catch (NoResultException ex) {
			return unsuccessful(HttpStatus.NOT_FOUND, "User doesn't exist, id: " + id, ex);
		} catch (Exception e) {
			return unsuccessful(HttpStatus.INTERNAL_SERVER_ERROR, "User deleting (disabling) failed, user id:"+id, e);
		}
	}


	/**
	 * Get un-hidden users list
	 * <p>
	 * @return - JSON with list of active (un-hidden) users or response status 500 on fail
	 */
	@GetMapping("v1/users")
	@SuppressWarnings("unchecked")
	public ResponseEntity getUsers() {
		try {
			List<User> users = userService.get();
			return new ResponseEntity(users, HttpStatus.OK);
		} catch (Exception e) {
			return unsuccessful(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get users list", e);
		}
	}


	/**
	 * Get User information by user identifier
	 * <p>
	 * @param id - user identifier
	 * @return -  json with user information (by identifier) or response status 500 on fail
	 */
	@GetMapping("v1/users/{id}")
	@SuppressWarnings("unchecked")
	public ResponseEntity getUserById(@PathVariable("id") UUID id) {
		try {
			User user = userService.get(id);
			return new ResponseEntity(user, HttpStatus.OK);
		} catch (NoResultException ex){
			return unsuccessful(HttpStatus.NOT_FOUND, "User doesn't exist, id: "+id, ex);
		} catch (Exception e) {
			return unsuccessful(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get user by id :"+id, e);
		}
	}

	/**
	 * Log error & return response entity
	 * </p>
	 * @param httpStatus http status
	 * @param message error message
	 * @param e error
	 * @return response entity
	 */
	private ResponseEntity unsuccessful(HttpStatus httpStatus, String message,  Throwable e){
		logger.error(message, e);
		return new ResponseEntity(httpStatus);
	}


}

