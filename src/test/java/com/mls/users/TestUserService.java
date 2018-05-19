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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
	 * Setup before tests
	 * @throws Exception on fail
	 */
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}


	/**
	 * Common test
	 * @throws Exception on fail
	 */
	@Test
	public void testUserServiceControllers() throws Exception {

		UUID id = UUID.randomUUID();

		String random = String.valueOf(Math.random()).substring(2, 13);
		User user = new User();
		user.setId(id);
		user.setUserName("user_" + random);
		user.setAddress("address" + random);
		user.setEmail("test_888@mail.com");
		user.setHidden(false);
		user.setPhone("+" + random);

		//password must be encoded (see spec. on swgger.yalm)
		user.setPassword(new Md5PasswordEncoder()
				.encodePassword("12345", Configuration.PASSWORD_SALT + random));

		//Test user CREATE operation
		mockMvc.perform(post(REQUEST_PATH, user)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(userJson.write(user).getJson()))
				.andExpect(status().isCreated())
				.andDo(print());

		//Test user GET operation
		String getPath = REQUEST_PATH + "/" + id;
		mockMvc.perform(get(getPath))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());

		//Test user UPDATE operation
		JSONObject data = new JSONObject();
		data.put("userName", "updated_" + random);
		data.put("address", "updated_" + random);

		String updatePath = REQUEST_PATH + "/" + id;
		mockMvc.perform(put(updatePath)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(String.valueOf(data)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());

		//Test user DELETE operation
		String deletePath = REQUEST_PATH + "/" + id;
		mockMvc.perform(delete(deletePath))
				.andExpect(status().isOk())
				.andDo(print());
	}


	/**
	 * Test CREATE operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore
	public void testCreateUser() throws Exception {

		UUID id = UUID.randomUUID();
		String random = String.valueOf(Math.random()).substring(2, 13);
		User user = new User();
		user.setId(id);
		user.setUserName("user_" + random);
		user.setAddress("address" + random);
		user.setEmail("test_888@mail.com");
		user.setHidden(false);
		user.setPhone("+" + random);

		//here is just an example to remind not to store not encoded passwords
		user.setPassword(new Md5PasswordEncoder()
				.encodePassword("12345", Configuration.PASSWORD_SALT));

		mockMvc.perform(post(REQUEST_PATH, user)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(userJson.write(user).getJson()))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	/**
	 * Test GET ALL operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore
	public void testGetUsers() throws Exception {
		mockMvc.perform(get(REQUEST_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}

	/**
	 * Test GET-BY-ID operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore
	public void testGetUserById() throws Exception {

		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";
		String getPath = REQUEST_PATH + "/" + id;
		mockMvc.perform(get(getPath))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}


	/**
	 * Test UPDATE operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore
	public void testUpdateUser() throws Exception {

		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";

		JSONObject data = new JSONObject();
		data.put("userName", "updated_" + System.currentTimeMillis());
		data.put("address", "address_" + System.currentTimeMillis());

		String updatePath = REQUEST_PATH + "/" + id;
		mockMvc.perform(put(updatePath)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(String.valueOf(data)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print());
	}

	/**
	 * Test DELETE operation separately
	 * @throws Exception on fail
	 */
	@Test
	@Ignore
	public void testDeleteUser() throws Exception {

		String id = "88492474-f594-4b08-84a0-bff40f9fd9cb";
		String deletePath = REQUEST_PATH + "/" + id;
		mockMvc.perform(delete(deletePath))
				.andExpect(status().isOk())
				.andDo(print());
	}
}