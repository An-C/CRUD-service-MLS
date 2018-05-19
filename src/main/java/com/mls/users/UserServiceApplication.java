package com.mls.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main entry point of the spring boot application
 * </p>
 * @author Anastasya Chizhikova
 * @since 17.05.2018
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
