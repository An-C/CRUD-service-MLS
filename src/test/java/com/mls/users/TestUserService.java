package com.mls.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mls.users.configuration.Configuration;
import com.mls.users.model.User;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Test user service controller
 * </p>
 * @author Anastasya Chizhikova
 * @since 17.05.2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUserServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUserService {

	/**
	 * mock mvc
	 */
	private MockMvc mockMvc;

	/**
	 * Web Application Context
	 */
	@Autowired
	private WebApplicationContext webApplicationContext;

	/**
	 * Jackson Tester
	 */
	private JacksonTester<User> userJson;

	/**
	 * Common request path
	 */
	private final static String REQUEST_PATH = "/v1/users";

	/**
	 * User to test
	 */
	private User user;


	/**
	 * Setup before test
	 * </p>
	 * @throws Exception on fail
	 */
	@Before
	public void setup() throws Exception {
		//setup spring mock context
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		//init JacksonTester json mapping objects
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

		//init user
		user = new User();
		user.setId(UUID.randomUUID());
		user.setUserName("user_" + System.currentTimeMillis());
		user.setAddress("address" + System.currentTimeMillis());
		user.setEmail("test_888@mail.com");
		user.setHidden(false);
		user.setPhone("+12223333333");

		//password suggested to be encoded (see spec. on swagger.ylm)
		user.setPassword(new Md5PasswordEncoder()
				.encodePassword("12345", Configuration.PASSWORD_SALT + user.getUserName()));
	}


	/**
	 * Test all operations on User
	 * </p>
	 * @throws Exception on fail
	 */
	@Test
	public void testUserServiceControllers() throws Exception {

		//Test user CREATE operation ---
		createUser(user, status().isCreated());


		//Test user GET operation
		getUser(String.valueOf(user.getId()), status().isOk());  //Success
		getUser("ABC", status().isBadRequest());             //Invalid argument
		getUser(String.valueOf(UUID.randomUUID()), status().isNotFound()); //Nonexistent user


		//Test user UPDATE operation ---
		JSONObject data = new JSONObject();
		data.put("userName", "updated_" + System.currentTimeMillis());
		data.put("address", "updated_" + System.currentTimeMillis());

		updateUser(String.valueOf(user.getId()), data, status().isOk()); //Success
		updateUser("ABC", data, status().isBadRequest());            //Invalid argument
		updateUser(String.valueOf(UUID.randomUUID()), data, status().isNotFound()); //Nonexistent user


		//Test user DELETE operation ---
		deleteUser(String.valueOf(user.getId()), status().isOk()); //Success
		deleteUser("ABC", status().isBadRequest());            //Invalid argument
		deleteUser(String.valueOf(user.getId()), status().isNotFound());//Nonexistent user

	}


	/**
	 * Test CREATE operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore //comment to test
	public void testCreateUser() throws Exception {
		createUser(user, status().isCreated());
	}

	/**
	 * Test GET-BY-ID operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore //comment to test
	public void testGetUserById() throws Exception {

		//here must be an identifier of existing, not disabled user
		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";

		getUser(id, status().isOk()); //Success
		getUser("ABC", status().isBadRequest()); //Invalid argument
		getUser(String.valueOf(UUID.randomUUID()), status().isNotFound()); //Nonexistent user

	}

	/**
	 * Test GET ALL operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore //comment to test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get(REQUEST_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}


	/**
	 * Test UPDATE operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore //comment to test
	public void testUpdateUser() throws Exception {
		//here must be an identifier of existing, not disabled user
		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";

		JSONObject data = new JSONObject();
		data.put("userName", "updated_" + System.currentTimeMillis());
		data.put("address", "updated_" + System.currentTimeMillis());

		updateUser(id, data, status().isOk()); //Expect success
		updateUser(String.valueOf(UUID.randomUUID()), data, status().isBadRequest()); //Nonexistent user
	}

	/**
	 * Test DELETE operation separately
	 * </p>
	 * @throws Exception on fail
	 */
	@Test
	@Ignore //comment to test
	public void testDeleteUser() throws Exception {
		//here must be an identifier of existing, not disabled user
		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";

		deleteUser(id, status().isOk());                           //Success
		deleteUser("ABC", status().isBadRequest());            //Invalid argument
		deleteUser(String.valueOf(user.getId()), status().isNotFound());//Nonexistent user
	}


	/**
	 * CREATE operation on User
	 * </p>
	 * @param user   - user
	 * @param status - expected http status
	 * @throws Exception on fail
	 */
	private void createUser(User user, ResultMatcher status) throws Exception {
		mockMvc.perform(post(REQUEST_PATH, user)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(userJson.write(user).getJson()))
				.andExpect(status)
				.andDo(print());
	}

	/**
	 * GET operation on User by user identifier
	 * </p>
	 * @param id     - user identifier
	 * @param status - expected http status
	 * @throws Exception on fail
	 */
	private void getUser(String id, ResultMatcher status) throws Exception {
		String getPath = REQUEST_PATH + "/" + id;
		ResultActions getOperation = mockMvc.perform(get(getPath))
				.andExpect(status)
				.andDo(print());

		//if OK, check content type
		if (getOperation.andReturn().getResponse().getStatus() == HttpStatus.OK.value()) {
			getOperation.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		}
	}

	/**
	 * UPDATE operation on User by user identifier
	 * </p>
	 * @param id     - user identifier
	 * @param fields - user fields with values to update in json format
	 * @param status - expected http status
	 * @throws Exception on fails
	 */
	private void updateUser(String id, JSONObject fields, ResultMatcher status) throws Exception {

		String updatePath = REQUEST_PATH + "/" + id;
		ResultActions updateOperation = mockMvc.perform(put(updatePath)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(String.valueOf(fields)))
				.andExpect(status)
				.andDo(print());

		//if OK, check content type and updated fields
		if (updateOperation.andReturn().getResponse().getStatus() == HttpStatus.OK.value()) {
			updateOperation.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
			fields.keys().forEachRemaining(n -> {
						try {
							String key = String.valueOf(n);
							updateOperation.andExpect(jsonPath(key).value(String.valueOf(fields.get(key))));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			);
		}
	}

	/**
	 * DELETE operation on User by user identifier
	 * </p>
	 * @param id     - user identifier
	 * @param status - expected http status
	 * @throws Exception on fails
	 */
	private void deleteUser(String id, ResultMatcher status) throws Exception {
		String deletePath = REQUEST_PATH + "/" + id;
		mockMvc.perform(delete(deletePath))
				.andExpect(status)
				.andDo(print());
	}
}