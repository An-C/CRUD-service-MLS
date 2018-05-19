package com.mls.users.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;


/**
 * User entity
 * </p>
 * To not overload the example I decided to limit this model with just a few fields
 * and not include fields like name, lastName, middleName, department, creation and updating time and so on
 * </p>
 * @author Anastasya Chizhikova
 * @since 16.05.2018
 */

@Entity
@Table(name = "userinfo")
public class User implements Serializable {

	/**
	 * User identifier
	 */
	@Id
	@Type(type = "pg-uuid")
	@Column(name = "id", nullable = false, unique = true)
	@JsonProperty("id")
	private UUID id;


	/**
	 * User login
	 */
	@Column(name = "username", nullable = false, unique = true)
	@JsonProperty("userName")
	private String userName;


	/**
	 * User password (MD5Hash)
	 */
	@Column(name = "pwd", length = 32)
	@JsonProperty("pwd")
	private String password;


	/**
	 * User address
	 */
	@Column(name = "address")
	@JsonProperty("address")
	private String address;


	/**
	 * User phone
	 */
	@Column(name = "phone", length = 12)
	@JsonProperty("phone")
	private String phone;


	/**
	 * User e-mail
	 */
	@Column(name = "email")
	@JsonProperty("email")
	private String email;


	/**
	 * 'Hidden user' flag
	 */
	@Column(name = "hide")
	@JsonProperty("hidden")
	private Boolean hidden;


	/**
	 * Constructor
	 */
	public User() {
	}


	//	Getters and setters


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}



	@Override
	public String toString() {
		return new org.apache.commons.lang3.builder.ToStringBuilder(this)
				.append("id", id)
				.append("userName", userName)
				.append("password", password)
				.append("address", address)
				.append("phone", phone)
				.append("email", email)
				.append("hidden", hidden)
				.toString();
	}
}
